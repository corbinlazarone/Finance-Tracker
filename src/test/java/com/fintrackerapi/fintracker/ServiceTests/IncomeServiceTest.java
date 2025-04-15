package com.fintrackerapi.fintracker.ServiceTests;

import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.entities.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * TEST CASES
 * - Test successful get all income sources
 * - Test empty get all income sources
 * - Test create income source with valid income source
 * - Test create income source with invalid income source
 * - Test update income source with valid income source
 * - Test update income source with invalid income source
 * - Test successful delete income source
 * - Test failed delete income source
 */

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    private IncomeRepo incomeRepo;

    @Mock
    private UserRepo userRepo;

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
}
