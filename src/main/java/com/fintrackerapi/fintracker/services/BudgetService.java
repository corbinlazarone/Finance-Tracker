package com.fintrackerapi.fintracker.services;

import com.fintrackerapi.fintracker.dtos.BudgetDto;
import com.fintrackerapi.fintracker.dtos.CategoryDto;
import com.fintrackerapi.fintracker.dtos.TransactionDto;
import com.fintrackerapi.fintracker.entities.Budget;
import com.fintrackerapi.fintracker.entities.Category;
import com.fintrackerapi.fintracker.entities.Transaction;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.exceptions.NotPermittedException;
import com.fintrackerapi.fintracker.exceptions.ResourceNotFoundException;
import com.fintrackerapi.fintracker.exceptions.UserNotFoundException;
import com.fintrackerapi.fintracker.interfaces.BudgetInterface;
import com.fintrackerapi.fintracker.repositories.BudgetRepo;
import com.fintrackerapi.fintracker.repositories.CategoryRepo;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.BudgetResponse;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BudgetService implements BudgetInterface {
    private final BudgetRepo budgetRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    public BudgetService(BudgetRepo budgetRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.budgetRepo = budgetRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public BudgetResponse createNewBudget(UUID userId, BudgetDto budgetDto) {
        Budget newBudget = new Budget();
        newBudget.setName(budgetDto.getName());
        newBudget.setBudgetMonth(budgetDto.getBudgetMonth());
        newBudget.setFirstBiWeeklyAmount(budgetDto.getFirstBiWeeklyAmount());
        newBudget.setSecondBiWeeklyAmount(budgetDto.getSecondBiWeeklyAmount());
        newBudget.setStartDate(budgetDto.getStartDate());
        newBudget.setEndDate(budgetDto.getEndDate());
        newBudget.setActive(budgetDto.isActive());

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));

        newBudget.setUser(user);

        Budget savedBudget = budgetRepo.save(newBudget);

        return convertToBudgetResponse(savedBudget);
    }

    @Override
    public CategoryResponse createNewCategory(UUID userId, CategoryDto categoryDto) {
        Category newCategory = new Category();
        newCategory.setName(categoryDto.getName());
        newCategory.setDescription(categoryDto.getDescription());
        newCategory.setAmountAllocated(categoryDto.getAmountAllocated());

        // find user's budget
        Budget budget = budgetRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find user's budget"));

        // set budget reference
        newCategory.setBudget(budget);

        // Get the existing categories or create a new set if null
        Set<Category> categories = budget.getCategories();
        if (categories == null) {
            categories = new HashSet<>();
            budget.setCategories(categories);
        }
        categories.add(newCategory);

        // save budget will also cascade to categories
        budgetRepo.save(budget);

        return convertToCategoryResponse(newCategory);
    }

    @Override
    public TransactionResponse createNewTransaction(UUID userId, UUID categoryId, TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setName(transactionDto.getName());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setMemo(transactionDto.getMemo());
        transaction.setType(transactionDto.getType());

        // find user's budget
        Budget budget = budgetRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find user's budget"));

        // set budget reference
        transaction.setBudget(budget);

        // find category in budget
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Category"));

        UUID budgetId = category.getBudget().getId();
        if (!category.getBudget().getId().equals(budgetId)) {
            throw new NotPermittedException("Category does not belong to user's budget");
        }

        transaction.setCategory(category);

        // update amount spent in category
        category.setAmountSpent(category.getAmountSpent().add(transaction.getAmount()));
        categoryRepo.save(category);

        // Get existing transactions or create a new set if null
        Set<Transaction> transactions = budget.getTransactions();
        if (transactions == null) {
            transactions = new HashSet<>();
            budget.setTransactions(transactions);
        }
        transactions.add(transaction);

        // Save budget will also cascade to transactions
        budgetRepo.save(budget);

        return convertToTransactionResponse(transaction);
    }

    @Override
    public BudgetResponse getBudget(UUID userId) {
        Optional<Budget> budget =  budgetRepo.findByUserId(userId);
        return budget
                .map(this::convertToBudgetResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Budget does not exist"));
    }

    @Override
    public Set<CategoryResponse> getCategories(UUID userId) {
        // find user's budget
        Budget budget = budgetRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find user's budget"));

        Set<Category> categories = budget.getCategories();
        Set<CategoryResponse> categoryResponses = new HashSet<>();

        // Convert each Category to CategoryResponse
        for (Category category : categories) {
            categoryResponses.add(convertToCategoryResponse(category));
        }

        return categoryResponses;
    }

    @Override
    public Set<TransactionResponse> getAllTransactions(UUID userId) {
        // find user's budget
        Budget budget = budgetRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find user's budget"));

        Set<Transaction> transactions = budget.getTransactions();
        Set<TransactionResponse> transactionResponses = new HashSet<>();

        // Convert each Transaction to TransactionResponse
        for (Transaction transaction : transactions) {
            transactionResponses.add(convertToTransactionResponse(transaction));
        }

        return transactionResponses;
    }

    @Override
    public BudgetResponse updateBudget(UUID userId, BudgetDto budgetDto) {
        return null;
    }

    @Override
    public CategoryResponse updateCategories(UUID userId, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public TransactionResponse updateTransaction(UUID userId, TransactionDto transactionDto) {
        return null;
    }

    @Override
    public boolean deleteCategory(UUID userId, UUID categoryId) {
        return false;
    }

    @Override
    public boolean deleteTransaction(UUID userId, UUID transactionId) {
        return false;
    }

    @Override
    public boolean deleteBudget(UUID userId, UUID budgetId) {
        return true;
    }

    private BudgetResponse convertToBudgetResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getName(),
                budget.getBudgetMonth(),
                budget.getFirstBiWeeklyAmount(),
                budget.getSecondBiWeeklyAmount(),
                budget.getStartDate(),
                budget.getEndDate(),
                budget.isActive(),
                budget.getCreatedAt(),
                budget.getUpdatedAt()
        );
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getAmountAllocated(),
                category.getAmountSpent(),
                category.getCreatedAt(),
                category.getUpdated_at()
        );
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getName(),
                transaction.getAmount(),
                transaction.getMemo(),
                transaction.getType(),
                transaction.getCreatedAt(),
                transaction.getUpdated_at()
        );
    }
}
