package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.components.IncomeConverter;
import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.InvalidPaymentDateException;
import com.fintrackerapi.fintracker.exceptions.NotPermittedException;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.exceptions.UserNotFoundException;
import com.fintrackerapi.fintracker.interfaces.IncomeInterface;
import com.fintrackerapi.fintracker.repositories.IncomeRepo;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IncomeService implements IncomeInterface {

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private IncomeConverter incomeConverter;

    @Override
    public List<IncomeResponse> getIncomeSources(UUID userId) {
        List<IncomeResponse> incomeSourceResponses = new ArrayList<>();

        incomeRepo.findByUserId(userId).forEach(income -> {
            incomeSourceResponses.add(incomeConverter.convertToIncomeResponse(income));
        });
        return incomeSourceResponses;
    }

    @Override
    public IncomeResponse createIncomeSource(UUID userId, IncomeDto incomeDto) {
        checkPaymentDatesAreValid(incomeDto);
        Income newIncome = incomeConverter.convertToIncomeEntity(incomeDto);
        User user = checkIfUserExists(userId);
        newIncome.setUser(user);
        Income savedIncome = incomeRepo.save(newIncome);

        return incomeConverter.convertToIncomeResponse(savedIncome);
    }

    @Override
    public IncomeResponse updateIncomeSource(UUID userId, UUID incomeSourceId, IncomeDto updatedIncomeSource) {
        Income income = checkIfIncomeSourceExists(incomeSourceId);
        checkIfIncomeSourceBelongsToUser(income, userId);
        checkPaymentDatesAreValid(updatedIncomeSource);
        incomeConverter.convertToIncomeEntity(updatedIncomeSource);
        Income savedIncome = incomeRepo.save(income);

        return incomeConverter.convertToIncomeResponse(savedIncome);
    }

    @Override
    public boolean deleteIncomeSource(UUID userId, UUID incomeSourceId) {
        Income income = checkIfIncomeSourceExists(incomeSourceId);
        checkIfIncomeSourceBelongsToUser(income, userId);
        incomeRepo.deleteById(incomeSourceId);

        return true;
    }

    private void checkPaymentDatesAreValid(IncomeDto incomeDto) {
        if (incomeDto.getPaymentDateOne() < 1
                || incomeDto.getPaymentDateOne() > 31
                || incomeDto.getPaymentDateTwo() < 1
                || incomeDto.getPaymentDateTwo() > 31) {
            throw new InvalidPaymentDateException("Invalid Payment Date");
        }
    }

    private User checkIfUserExists(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " Not Found"));
    }

    private Income checkIfIncomeSourceExists(UUID incomeSourceId) {
        return incomeRepo.findById(incomeSourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Income source does not exists"));
    }

    private void checkIfIncomeSourceBelongsToUser(Income income, UUID userId) {
        UUID incomeUserId = income.getUser().getId();
        if (!incomeUserId.equals(userId)) {
            throw new NotPermittedException("You don't have permission to update this income source!");
        }
    }
}
