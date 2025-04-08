package com.fintrackerapi.fintracker.controllers;

import com.fintrackerapi.fintracker.dtos.BudgetDto;
import com.fintrackerapi.fintracker.dtos.CategoryDto;
import com.fintrackerapi.fintracker.dtos.TransactionDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.responses.BudgetResponse;
import com.fintrackerapi.fintracker.responses.CategoryResponse;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import com.fintrackerapi.fintracker.services.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/budget")
@RestController
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/create")
    public ResponseEntity<BudgetResponse> createNewBudget(@RequestBody BudgetDto budgetDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        BudgetResponse newBudget = budgetService.createNewBudget(currentUser.getId(), budgetDto);
        return ResponseEntity.status(201).body(newBudget);
    }

    @PostMapping("/category/create")
    public ResponseEntity<CategoryResponse> createNewCategory(@RequestBody CategoryDto categoryDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        CategoryResponse newCategory = budgetService.createNewCategory(currentUser.getId(), categoryDto);
        return ResponseEntity.status(201).body(newCategory);
    }

    @PostMapping("/category/{categoryId}/create")
    public ResponseEntity<TransactionResponse> createNewTransaction(@PathVariable UUID categoryId,
                                                                    @RequestBody TransactionDto transactionDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        TransactionResponse newTransaction = budgetService.createNewTransaction(currentUser.getId(),
                categoryId, transactionDto);
        return ResponseEntity.status(201).body(newTransaction);
    }

    @GetMapping("/get")
    public ResponseEntity<BudgetResponse> getBudget() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        BudgetResponse budgetResponse = budgetService.getBudget(currentUser.getId());
        return ResponseEntity.ok(budgetResponse);
    }

    @GetMapping("/categories")
    public ResponseEntity<Set<CategoryResponse>> getCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        Set<CategoryResponse> categoryResponse = budgetService.getCategories(currentUser.getId());
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Set<TransactionResponse>> getTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        Set<TransactionResponse> transactionResponses = budgetService.getAllTransactions(currentUser.getId());
        return ResponseEntity.ok(transactionResponses);
    }
}
