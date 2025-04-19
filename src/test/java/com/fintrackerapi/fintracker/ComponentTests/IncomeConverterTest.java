package com.fintrackerapi.fintracker.ComponentTests;

import com.fintrackerapi.fintracker.components.IncomeConverter;
import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IncomeConverterTest {

    private final IncomeConverter incomeConverter = new IncomeConverter();

    @Test
    public void convertToIncomeEntitySuccess() {

        IncomeDto mockDto = new IncomeDto();
        mockDto.setName("Test Income");
        mockDto.setBiweekly(true);
        mockDto.setAmount(BigDecimal.valueOf(2000));
        mockDto.setPaymentDateOne(1);
        mockDto.setPaymentDateTwo(15);

        Income convertedIncome = incomeConverter.convertToIncomeEntity(mockDto);

        assertNotNull(convertedIncome);
        assertEquals(convertedIncome.getName(), mockDto.getName());
        assertEquals(convertedIncome.getAmount(), mockDto.getAmount());
        assertEquals(convertedIncome.getIsBiWeekly(), mockDto.IsBiweekly());
        assertEquals(convertedIncome.getPaymentDateOne(), mockDto.getPaymentDateOne());
        assertEquals(convertedIncome.getPaymentDateTwo(), mockDto.getPaymentDateTwo());
    }

    @Test
    public void convertIncomeEntityFailure() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           incomeConverter.convertToIncomeEntity(null);
        });

        assertEquals("IncomeDto can not be null", exception.getMessage());
    }

    @Test
    public void convertIncomeResponseSuccess() {

        UUID incomeId = UUID.randomUUID();
        Income income = new Income();
        income.setId(incomeId);
        income.setName("Test income");
        income.setAmount(BigDecimal.valueOf(2000));
        income.setIsBiweekly(true);
        income.setPaymentDateOne(1);
        income.setPaymentDateTwo(15);
        income.setCreatedAt(new Date());
        income.setUpdated_at(new Date());

        IncomeResponse incomeResponse = incomeConverter.convertToIncomeResponse(income);

        assertNotNull(incomeResponse);
        assertEquals(incomeResponse.getId(), income.getId());
        assertEquals(incomeResponse.getName(), income.getName());
        assertEquals(incomeResponse.getAmount(), income.getAmount());
        assertEquals(incomeResponse.getCreatedAt(), income.getCreatedAt());
        assertEquals(incomeResponse.getUpdatedAt(), income.getUpdated_at());
    }

    @Test
    public void convertIncomeResponseFailure() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            incomeConverter.convertToIncomeResponse(null);
        });

        assertEquals("Income entity can not be null", exception.getMessage());
    }
}
