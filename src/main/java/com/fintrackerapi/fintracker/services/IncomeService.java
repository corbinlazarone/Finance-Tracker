package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.InvalidPaymentDateException;
import com.fintrackerapi.fintracker.exceptions.UserNotFoundException;
import com.fintrackerapi.fintracker.interfaces.IncomeInterface;
import com.fintrackerapi.fintracker.repositories.IncomeRepo;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IncomeService implements IncomeInterface {
    private final IncomeRepo incomeRepo;
    private final UserRepo userRepo;

    public IncomeService(IncomeRepo incomeRepo, UserRepo userRepo) {
        this.incomeRepo = incomeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<IncomeResponse> getIncomeSources(UUID userId) {
        List<IncomeResponse> incomeSourceResponses = new ArrayList<>();

        incomeRepo.findByUserId(userId).forEach(income -> {
            incomeSourceResponses.add(converToIncomeResponse(income));
        });
        return incomeSourceResponses;
    }

    @Override
    public IncomeResponse createIncomeSource(IncomeDto incomeDto) {
        Income newIncome = new Income();
        newIncome.setName(incomeDto.getName());
        newIncome.setAmount(incomeDto.getAmount());
        newIncome.setIsBiweekly(incomeDto.isBiweekly());

        if (incomeDto.getPaymentDateOne() < 1
                || incomeDto.getPaymentDateOne() > 31
                || incomeDto.getPaymentDateTwo() < 1
                || incomeDto.getPaymentDateTwo() > 31) {
            throw new InvalidPaymentDateException("Invalid Payment Payment Date for");
        }
        newIncome.setPaymentDateOne(incomeDto.getPaymentDateOne());
        newIncome.setPaymentDateTwo(incomeDto.getPaymentDateTwo());


        User user = userRepo.findById(incomeDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + incomeDto.getUserId() + " Not Found"));

        newIncome.setUser(user);

        Income savedIncome = incomeRepo.save(newIncome);
        return converToIncomeResponse(savedIncome);
    }

    @Override
    public IncomeResponse updateIncomeSource(UUID incomeSourceId, IncomeDto updatedIncomeSource) {
        // Check if the income source exists
        Income income = incomeRepo.findById(incomeSourceId)
                .orElseThrow(() -> new RuntimeException("Income source does not exits"));

        // Check if income source belongs to user
        UUID incomeUserId = income.getUser().getId();
        if (!incomeUserId.equals(updatedIncomeSource.getUserId())) {
            throw new RuntimeException("User does not have Permission to delete this income source");
        }

        if (updatedIncomeSource.getPaymentDateOne() < 1
                || updatedIncomeSource.getPaymentDateOne() > 31
                || updatedIncomeSource.getPaymentDateTwo() < 1
                || updatedIncomeSource.getPaymentDateTwo() > 31) {
            throw new InvalidPaymentDateException("Invalid Payment Date");
        }

        income.setName(updatedIncomeSource.getName());
        income.setAmount(updatedIncomeSource.getAmount());
        income.setIsBiweekly(updatedIncomeSource.isBiweekly());
        income.setPaymentDateOne(updatedIncomeSource.getPaymentDateOne());
        income.setPaymentDateTwo(updatedIncomeSource.getPaymentDateTwo());

        Income savedIncome = incomeRepo.save(income);

        return converToIncomeResponse(savedIncome);
    }

    @Override
    public boolean deleteIncomeSource(UUID userId, UUID incomeSourceId) {
        // Check if the income source exists
        Income income = incomeRepo.findById(incomeSourceId)
                .orElseThrow(() -> new RuntimeException("Income source does not exits"));

        // Check if income source belongs to user
        UUID incomeUserId = income.getUser().getId();
        if (!incomeUserId.equals(userId)) {
            throw new RuntimeException("User does not have Permission to delete this income source");
        }

        incomeRepo.deleteById(incomeSourceId);

        return true;
    }

    private IncomeResponse converToIncomeResponse(Income income) {
        return new IncomeResponse(
                income.getId(),
                income.getName(),
                income.getAmount(),
                income.getIsBiWeekly(),
                income.getPaymentDateOne(),
                income.getPaymentDateTwo()
        );
    }
}
