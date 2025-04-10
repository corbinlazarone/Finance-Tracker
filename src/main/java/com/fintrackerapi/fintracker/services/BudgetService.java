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
import com.fintrackerapi.fintracker.repositories.TransactionRepo;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import com.fintrackerapi.fintracker.responses.BudgetResponse;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BudgetService implements BudgetInterface {
    private final BudgetRepo budgetRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final TransactionRepo transactionRepo;

    public BudgetService(BudgetRepo budgetRepo, UserRepo userRepo, CategoryRepo categoryRepo, TransactionRepo transactionRepo) {
        this.budgetRepo = budgetRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.transactionRepo = transactionRepo;
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
    @Transactional
    public CategoryResponse createNewCategory(UUID userId, CategoryDto categoryDto) {
        Budget budget = checkBudget(userId);

        // Check if a category with the same name already exists in this budget
        String categoryName = categoryDto.getName().trim();
        if (categoryRepo.existsByBudgetIdAndNameIgnoreCase(budget.getId(), categoryName)) {
            throw new ResourceNotFoundException("A category with name '" + categoryName + "' already exists in this budget");
        }

        Category newCategory = new Category();
        newCategory.setName(categoryDto.getName());
        newCategory.setDescription(categoryDto.getDescription());
        newCategory.setAmountAllocated(categoryDto.getAmountAllocated());
        newCategory.setAmountSpent(BigDecimal.ZERO);
        newCategory.setDueDate(categoryDto.getDueDate());

        // set budget reference
        newCategory.setBudget(budget);

        Category savedCategory = categoryRepo.save(newCategory);

        return convertToCategoryResponse(savedCategory);
    }

    @Override
    @Transactional
    public TransactionResponse createNewTransaction(UUID userId, UUID categoryId, TransactionDto transactionDto) {
        Budget budget = checkBudget(userId);

        Transaction transaction = new Transaction();
        transaction.setName(transactionDto.getName());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setMemo(transactionDto.getMemo());
        transaction.setType(transactionDto.getType());

        // set budget reference
        transaction.setBudget(budget);

        // find category and check if it belongs to the user's budget
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

        Transaction savedTransaction = transactionRepo.save(transaction);

        return convertToTransactionResponse(savedTransaction);
    }

    @Override
    public BudgetResponse getBudget(UUID userId) {
        Optional<Budget> budget =  budgetRepo.findByUserId(userId);
        return budget
                .map(this::convertToBudgetResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Budget does not exist"));
    }

    @Override
    @Transactional
    public Set<CategoryResponse> getCategories(UUID userId) {
        Budget budget = checkBudget(userId);

        Set<Category> categories = budget.getCategories();
        Set<CategoryResponse> categoryResponses = new HashSet<>();

        // Convert each Category to CategoryResponse
        for (Category category : categories) {
            categoryResponses.add(convertToCategoryResponse(category));
        }

        return categoryResponses;
    }

    @Override
    @Transactional
    public Set<TransactionResponse> getAllTransactions(UUID userId) {
        Budget budget = checkBudget(userId);

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
        Budget budget = checkBudget(userId);

        budget.setName(budgetDto.getName());
        budget.setBudgetMonth(budgetDto.getBudgetMonth());
        budget.setFirstBiWeeklyAmount(budgetDto.getFirstBiWeeklyAmount());
        budget.setSecondBiWeeklyAmount(budgetDto.getSecondBiWeeklyAmount());
        budget.setStartDate(budgetDto.getStartDate());
        budget.setEndDate(budgetDto.getEndDate());
        budget.setActive(budgetDto.isActive());

        return convertToBudgetResponse(budget);
    }

    @Override
    public CategoryResponse updateCategories(UUID userId, UUID categoryId, CategoryDto categoryDto) {
        Budget budget = checkBudget(userId);

        // find category and check if it belongs to the user's budget
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Category"));

        UUID budgetId = category.getBudget().getId();
        if (!category.getBudget().getId().equals(budgetId)) {
            throw new NotPermittedException("Category does not belong to user's budget");
        }

        // TODO: update category (may need to update the set that is attached to the budget
        //  or just update the category in the db)

        return null;
    }

    @Override
    public TransactionResponse updateTransaction(UUID userId, UUID transactionId, TransactionDto transactionDto) {
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

    // Check if budget exits and if budget belongs to user
    private Budget checkBudget(UUID userId) {
        Budget budget = budgetRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget does not exists"));

        UUID budgetUserId = budget.getUser().getId();
        if (!budgetUserId.equals(userId)) {
            throw new NotPermittedException("You don't have permission to update this budget!");
        }

        return budget;
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
                category.getDueDate(),
                category.getCreatedAt(),
                category.getUpdatedAt()
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
                transaction.getUpdatedAt()
        );
    }
}
