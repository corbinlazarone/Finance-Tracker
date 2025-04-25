package com.fintrackerapi.fintracker.components;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.springframework.stereotype.Component;

@Component
public class IncomeConverter {

    private IncomeConverter() {}

    public static Income convertToIncomeEntity(IncomeDto incomeDto) {
        if (incomeDto == null) throw new RuntimeException("IncomeDto can not be null");
        Income income = new Income();
        income.setName(incomeDto.getName());
        income.setAmount(incomeDto.getAmount());
        income.setIsBiweekly(incomeDto.IsBiweekly());
        income.setPaymentDateOne(incomeDto.getPaymentDateOne());
        income.setPaymentDateTwo(incomeDto.getPaymentDateTwo());

        return income;
    }

    public static IncomeResponse convertToIncomeResponse(Income income) {
        if (income == null) throw new RuntimeException("Income entity can not be null");

        return new IncomeResponse(
                income.getId(),
                income.getName(),
                income.getAmount(),
                income.getIsBiWeekly(),
                income.getPaymentDateOne(),
                income.getPaymentDateTwo(),
                income.getCreatedAt(),
                income.getUpdated_at()
        );
    }
}