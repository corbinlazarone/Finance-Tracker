package com.fintrackerapi.fintracker.UnitTests.ServiceTests;

import com.fintrackerapi.fintracker.components.IncomeConverter;
import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.InvalidPaymentDateException;
import com.fintrackerapi.fintracker.exceptions.UserNotFoundException;
import com.fintrackerapi.fintracker.repositories.IncomeRepo;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import com.fintrackerapi.fintracker.services.IncomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IncomeServiceUnitTest {

    @Mock
    private IncomeRepo incomeRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private IncomeConverter incomeConverter;

    @InjectMocks
    private IncomeService incomeService;

    @Test
    public void getIncomeSourcesSuccess() {

        // Setting up mock user reference
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        // Setting up mock income source data
        List<Income> incomes = new ArrayList<>();
        UUID incomeId1 = UUID.randomUUID();
        Date income1CreatedDate = new Date();
        Date income1UpdatedDate = new Date();
        UUID incomeId2 = UUID.randomUUID();
        Date income2CreatedDate = new Date();
        Date income2UpdatedDate = new Date();

        Income income1 = new Income();
        income1.setId(incomeId1);
        income1.setName("Income1 Name");
        income1.setAmount(BigDecimal.valueOf(123));
        income1.setIsBiweekly(true);
        income1.setPaymentDateOne(1);
        income1.setPaymentDateTwo(15);
        income1.setUpdated_at(income1CreatedDate);
        income1.setCreatedAt(income1UpdatedDate);
        income1.setUser(user);

        Income income2 = new Income();
        income2.setId(incomeId2);
        income2.setName("Income2 Name");
        income2.setAmount(BigDecimal.valueOf(123));
        income2.setIsBiweekly(true);
        income2.setPaymentDateOne(1);
        income2.setPaymentDateTwo(15);
        income2.setCreatedAt(income2CreatedDate);
        income2.setUpdated_at(income2UpdatedDate);
        income2.setUser(user);

        incomes.add(income1);
        incomes.add(income2);

        IncomeConverter.convertToIncomeResponse(income1);
        IncomeConverter.convertToIncomeResponse(income2);

        // Mock findByUserId to return mock income sources
        when(incomeRepo.findByUserId(userId)).thenReturn(incomes);

        // Call the getIncomeSources method
        List<IncomeResponse> result = incomeService.getIncomeSources(user.getId());

        // Assert that the result is not null and has a size of 2
        assertNotNull(result);
        assertEquals(2, result.size());

        // Assert that income details are correct
        IncomeResponse income1Response = result.get(0);
        assertEquals(incomeId1, income1Response.getId());
        assertEquals(income1.getName(), income1Response.getName());
        assertEquals(income1.getAmount(), income1Response.getAmount());
        assertEquals(income1.getIsBiWeekly(), income1Response.isBiweekly());
        assertEquals(income1.getPaymentDateOne(), income1Response.getPaymentDateOne());
        assertEquals(income1.getPaymentDateTwo(), income1Response.getPaymentDateTwo());
        assertEquals(income1.getCreatedAt(), income1Response.getCreatedAt());
        assertEquals(income1.getUpdated_at(), income1Response.getUpdatedAt());

        IncomeResponse income2Response = result.get(1);
        assertEquals(incomeId2, income2Response.getId());
        assertEquals(income2.getName(), income2Response.getName());
        assertEquals(income2.getAmount(), income2Response.getAmount());
        assertEquals(income2.getIsBiWeekly(), income2Response.isBiweekly());
        assertEquals(income2.getPaymentDateOne(), income2Response.getPaymentDateOne());
        assertEquals(income2.getPaymentDateTwo(), income2Response.getPaymentDateTwo());
        assertEquals(income2.getCreatedAt(), income2Response.getCreatedAt());
        assertEquals(income2.getUpdated_at(), income2Response.getUpdatedAt());

        // Verify that findByUserId was only called once
        verify(incomeRepo, times(1)).findByUserId(userId);
    }

    @Test
    public void getIncomeSourceFailure() {
        UUID userId = UUID.randomUUID();

        // Mock findByUserId to return empty users list
        when(incomeRepo.findByUserId(userId)).thenReturn(Collections.emptyList());

        // Call the getIncomeSources method
        List<IncomeResponse> result = incomeService.getIncomeSources(userId);

        // Assert that the result is not null but is empty
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify that the income repo was only called once
        verify(incomeRepo, times(1)).findByUserId(userId);
    }

    @Test
    public void createIncomeSourceWithValidIncome() {

        // Mock data
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setName("Income Name");
        incomeDto.setAmount(BigDecimal.valueOf(123));
        incomeDto.setBiweekly(true);
        incomeDto.setPaymentDateOne(1);
        incomeDto.setPaymentDateTwo(15);

        Income savedIncome = new Income();
        savedIncome.setId(UUID.randomUUID());
        savedIncome.setName(incomeDto.getName());
        savedIncome.setAmount(incomeDto.getAmount());
        savedIncome.setIsBiweekly(true);
        savedIncome.setPaymentDateOne(1);
        savedIncome.setPaymentDateTwo(15);
        savedIncome.setUser(user);
        savedIncome.setCreatedAt(new Date());
        savedIncome.setUpdated_at(new Date());

        IncomeConverter.convertToIncomeEntity(incomeDto);
        IncomeConverter.convertToIncomeResponse(savedIncome);

        // Mock user repo to check if user exists
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        // Mock income repo save method
        when(incomeRepo.save(any(Income.class))).thenReturn(savedIncome);

        // Call the createIncomeSource method
        IncomeResponse newIncome = incomeService.createIncomeSource(userId, incomeDto);

        // Assert that newIncome is not null
        assertNotNull(newIncome);

        // Assert that saved income details are correct
        assertEquals(incomeDto.getName(), newIncome.getName());
        assertEquals(incomeDto.getAmount(), newIncome.getAmount());
        assertEquals(incomeDto.IsBiweekly(), newIncome.isBiweekly());
        assertEquals(incomeDto.getPaymentDateOne(), newIncome.getPaymentDateOne());

        // Verify that the income repo save method was only called once
        verify(incomeRepo, times(1)).save(any(Income.class));

        // Verify that user repo findById was only called once
        verify(userRepo, times(1)).findById(userId);
    }

    @Test
    public void createIncomeSourceInvalidPaymentDates() {

        // Mock data
        UUID userId = UUID.randomUUID();
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setPaymentDateOne(-1);
        incomeDto.setPaymentDateTwo(32);

        // Call createIncomeSource service method
        InvalidPaymentDateException exception = assertThrows(InvalidPaymentDateException.class, () -> {
           incomeService.createIncomeSource(userId, incomeDto);
        });

        // Assert that the exception messages matches
        assertEquals("Invalid Payment Date", exception.getMessage());

        // Verify that user and income repo were never called
        verify(userRepo, times(0)).findById(userId);
        verify(incomeRepo, times(0)).save(any(Income.class));
    }

    @Test
    public void createIncomeSourceUserDoesNotExists() {

        // Mock data
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setName("Income Name");
        incomeDto.setAmount(BigDecimal.valueOf(123));
        incomeDto.setBiweekly(true);
        incomeDto.setPaymentDateOne(1);
        incomeDto.setPaymentDateTwo(15);

        // Call createIncomeSource service method
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            incomeService.createIncomeSource(userId, incomeDto);
        });

        // Assert that the exception message matches
        assertEquals("User with id: " + userId + " Not Found", exception.getMessage());

        // Verify user repo was only called once and income repo was never called
        verify(userRepo, times(1)).findById(userId);
        verify(incomeRepo, times(0)).save(any(Income.class));
    }
}