package com.fintrackerapi.fintracker.UnitTests.ServiceTests;

import com.fintrackerapi.fintracker.components.CategoryConverter;
import com.fintrackerapi.fintracker.components.CheckBudget;
import com.fintrackerapi.fintracker.components.TransactionConverter;
import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.entities.Transaction;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.enums.TransactionType;
import com.fintrackerapi.fintracker.repositories.CategoryRepo;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import com.fintrackerapi.fintracker.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TEST CASES
 * - Test get categories not empty
 * - Test get categories empty
 * - Test get transactions by category not empty list
 * - Test get transactions by category an empty list
 */

@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTest {

    @Mock
    private CheckBudget checkBudget;

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Category Test Name");
    }

    @Test
    public void getCategoriesNotEmpty() {
        UUID budgetId = UUID.randomUUID();

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Budget checkedBudget = new Budget();
        checkedBudget.setId(budgetId);
        checkedBudget.setName("Budget Test Name");

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(category);
        checkedBudget.setCategories(categorySet);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());

        when(checkBudget.checkBudgetExistsAndBelongsToUser(userId)).thenReturn(checkedBudget);

        Set<CategoryResponse> result = categoryService.getCategories(userId);

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);

        CategoryResponse individualResponse = result.iterator().next();

        assertEquals(individualResponse.getId(), categoryResponse.getId());
        assertEquals(individualResponse.getName(), categoryResponse.getName());
    }

    @Test
    public void getCategoriesEmpty() {
        UUID budgetId = UUID.randomUUID();

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Budget checkedBudget = new Budget();
        checkedBudget.setId(budgetId);
        checkedBudget.setName("Budget Test Name");
        checkedBudget.setCategories(new HashSet<>());

        when(checkBudget.checkBudgetExistsAndBelongsToUser(userId)).thenReturn(checkedBudget);

        Set<CategoryResponse> result = categoryService.getCategories(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getTransactionsByCategoryNotEmpty() {
        UUID userId = UUID.randomUUID();
        UUID budgetId = UUID.randomUUID();

        Budget budget = new Budget();
        budget.setId(budgetId);

        List<Transaction> mockTransactions = new ArrayList<>();

        UUID transaction1Id = UUID.randomUUID();
        UUID transaction2Id = UUID.randomUUID();

        Transaction transaction1 = new Transaction();
        transaction1.setId(transaction1Id);
        transaction1.setName("Transaction 1 Name");
        transaction1.setAmount(BigDecimal.valueOf(50.45));
        transaction1.setMemo("Transaction 1 test memo");
        transaction1.setType(TransactionType.EXPENSE);
        transaction1.setCreatedAt(new Date());
        transaction1.setUpdatedAt(new Date());

        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction2Id);
        transaction2.setName("Transaction 2 Name");
        transaction2.setAmount(BigDecimal.valueOf(50.45));
        transaction2.setMemo("Transaction 2 test memo");
        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setCreatedAt(new Date());
        transaction2.setUpdatedAt(new Date());

        mockTransactions.add(transaction1);
        mockTransactions.add(transaction2);

        category.setTransactions(mockTransactions);

        when(checkBudget.checkBudgetExistsAndBelongsToUser(userId)).thenReturn(budget);
        when(categoryRepo.findById(category.getId())).thenReturn(Optional.of(category));

        TransactionConverter.convertToTransactionResponse(transaction1);
        TransactionConverter.convertToTransactionResponse(transaction2);

        List<TransactionResponse> foundTransactions = categoryService.getTransactionsByCategory(userId, category.getId());

        assertNotNull(foundTransactions);
        assertEquals(2, foundTransactions.size());

        assertEquals(transaction1Id, foundTransactions.get(0).getId());
        assertEquals(transaction1.getName(), foundTransactions.get(0).getName());
        assertEquals(transaction1.getAmount(), foundTransactions.get(0).getAmount());
        assertEquals(transaction1.getMemo(), foundTransactions.get(0).getMemo());
        assertEquals(transaction1.getType(), foundTransactions.get(0).getType());
        assertEquals(transaction1.getCreatedAt(), foundTransactions.get(0).getCreatedAt());
        assertEquals(transaction1.getUpdatedAt(), foundTransactions.get(0).getUpdatedAt());

        assertEquals(transaction2Id, foundTransactions.get(1).getId());
        assertEquals(transaction2.getName(), foundTransactions.get(1).getName());
        assertEquals(transaction2.getAmount(), foundTransactions.get(1).getAmount());
        assertEquals(transaction2.getMemo(), foundTransactions.get(1).getMemo());
        assertEquals(transaction2.getType(), foundTransactions.get(1).getType());
        assertEquals(transaction2.getCreatedAt(), foundTransactions.get(1).getCreatedAt());
        assertEquals(transaction2.getUpdatedAt(), foundTransactions.get(1).getUpdatedAt());

        verify(checkBudget, times(1)).checkBudgetExistsAndBelongsToUser(userId);
        verify(categoryRepo, times(1)).findById(category.getId());
    }

    @Test
    public void getEmptyTransactionsByCategory() {
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Budget budget = new Budget();
        budget.setId(UUID.randomUUID());

        when(checkBudget.checkBudgetExistsAndBelongsToUser(userId)).thenReturn(budget);
        when(categoryRepo.findById(categoryId)).thenReturn(Optional.empty());

        List<TransactionResponse> foundTransaction = categoryService.getTransactionsByCategory(userId, categoryId);

        assertNotNull(foundTransaction);
        assertTrue(foundTransaction.isEmpty());

        verify(checkBudget, times(1)).checkBudgetExistsAndBelongsToUser(userId);
        verify(categoryRepo, times(1)).findById(categoryId);
    }
}