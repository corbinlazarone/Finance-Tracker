package com.fintrackerapi.fintracker.ComponentTests;

import com.fintrackerapi.fintracker.components.CheckBudget;
import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.NotPermittedException;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.repositories.BudgetRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TEST CASES
 * - Test successfully budget exists
 * - Test budget does not exist
 * - Test successfully budget belongs to user
 * - Test budget does not belong to user
 */

@ExtendWith(MockitoExtension.class)
public class CheckBudgetTest {

    @Mock
    private BudgetRepo budgetRepo;

    @InjectMocks
    private CheckBudget checkBudget;

    @Test
    public void checkBudgetExistsSuccess() {

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        UUID budgetId = UUID.randomUUID();
        Budget budget = new Budget();
        budget.setId(budgetId);
        budget.setUser(user);
        budget.setName("Test Budget");
        budget.setBudgetMonth(YearMonth.of(2025, 4));
        budget.setFirstBiWeeklyAmount(BigDecimal.valueOf(2000));
        budget.setSecondBiWeeklyAmount(BigDecimal.valueOf(2000));
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.of(2025, 4, 30));
        budget.setActive(true);
        budget.setCreatedAt(new Date());
        budget.setUpdatedAt(new Date());

        when(budgetRepo.findByUserId(userId)).thenReturn(Optional.of(budget));

        Budget foundBudget = checkBudget.checkBudgetExistsAndBelongsToUser(userId);

        assertNotNull(foundBudget);
        assertEquals(foundBudget.getId(), budget.getId());
        assertEquals(foundBudget.getName(), budget.getName());
        assertEquals(foundBudget.getUser(), budget.getUser());
        assertEquals(foundBudget.getBudgetMonth(), budget.getBudgetMonth());
        assertEquals(foundBudget.getFirstBiWeeklyAmount(), budget.getFirstBiWeeklyAmount());
        assertEquals(foundBudget.getSecondBiWeeklyAmount(), budget.getSecondBiWeeklyAmount());
        assertEquals(foundBudget.getStartDate(), budget.getStartDate());
        assertEquals(foundBudget.getEndDate(), budget.getEndDate());
        assertEquals(foundBudget.isActive(), budget.isActive());
        assertEquals(foundBudget.getCreatedAt(), budget.getCreatedAt());
        assertEquals(foundBudget.getUpdatedAt(), budget.getUpdatedAt());

        verify(budgetRepo, times(1)).findByUserId(userId);
    }

    @Test
    public void checkBudgetExistsFailure() {
        UUID userId = UUID.randomUUID();

        when(budgetRepo.findByUserId(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
           checkBudget.checkBudgetExistsAndBelongsToUser(userId);
        });

        assertEquals("Budget does not exists", exception.getMessage());

        verify(budgetRepo, times(1)).findByUserId(userId);
    }

    @Test
    public void checkBudgetBelongsToUserSuccess() {

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        UUID budgetId = UUID.randomUUID();
        Budget budget = new Budget();
        budget.setId(budgetId);
        budget.setUser(user);
        budget.setName("Test Budget");
        budget.setBudgetMonth(YearMonth.of(2025, 4));
        budget.setFirstBiWeeklyAmount(BigDecimal.valueOf(2000));
        budget.setSecondBiWeeklyAmount(BigDecimal.valueOf(2000));
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.of(2025, 4, 30));
        budget.setActive(true);
        budget.setCreatedAt(new Date());
        budget.setUpdatedAt(new Date());

        when(budgetRepo.findByUserId(userId)).thenReturn(Optional.of(budget));

        Budget result = checkBudget.checkBudgetExistsAndBelongsToUser(userId);

        assertEquals(budget, result);
        verify(budgetRepo).findByUserId(userId);
    }

    @Test
    public void checkBudgetBelongsToUserFailure() {
        UUID userId = UUID.randomUUID();
        UUID differentUserId = UUID.randomUUID();

        User user = new User();
        user.setId(differentUserId);

        Budget budget = new Budget();
        budget.setId(UUID.randomUUID());
        budget.setUser(user);

        when(budgetRepo.findByUserId(userId)).thenReturn(Optional.of(budget));

        NotPermittedException exception = assertThrows(NotPermittedException.class, () -> {
           checkBudget.checkBudgetExistsAndBelongsToUser(userId);
        });

        assertEquals("You don't have permission to update this budget!", exception.getMessage());
    }
}