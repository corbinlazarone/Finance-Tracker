package com.fintrackerapi.fintracker.controllers;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import com.fintrackerapi.fintracker.services.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/income")
@RestController
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<IncomeResponse>> allUserIncomeSources(@RequestParam UUID userId) {
        List<IncomeResponse> incomeSources = incomeService.getIncomeSources(userId);
        return ResponseEntity.ok(incomeSources);
    }

    @PostMapping("/new")
    public ResponseEntity<IncomeResponse> createNewIncomeItem(@RequestBody IncomeDto incomeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        IncomeResponse newIncomeItem = incomeService.createIncomeSource(currentUser.getId(), incomeDto);
        return ResponseEntity.status(201).body(newIncomeItem);
    }

    @PutMapping("/update/{incomeSourceId}")
    public ResponseEntity<IncomeResponse> updateIncomeItem(@RequestBody IncomeDto incomeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        IncomeResponse IncomeItemToUpdate = incomeService.updateIncomeSource(currentUser.getId(), incomeDto);
        return ResponseEntity.ok(IncomeItemToUpdate);
    }

    @DeleteMapping("/delete/{incomeSourceId}")
    public ResponseEntity<Boolean> deleteIncomeSource(@PathVariable UUID incomeSourceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        boolean result = incomeService.deleteIncomeSource(currentUser.getId(), incomeSourceId);
        return ResponseEntity.ok(result);
    }
}