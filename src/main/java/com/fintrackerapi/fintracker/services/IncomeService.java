package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.interfaces.IncomeInterface;
import com.fintrackerapi.fintracker.repositories.IncomeRepo;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IncomeService implements IncomeInterface {
    private final IncomeRepo incomeRepo;

    public IncomeService(IncomeRepo incomeRepo) {
        this.incomeRepo = incomeRepo;
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
    public Boolean createIncomeSource(IncomeDto incomeDto) {
        return null;
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
