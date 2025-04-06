package com.fintrackerapi.fintracker.controllers;

import com.fintrackerapi.fintracker.responses.IncomeResponse;
import com.fintrackerapi.fintracker.services.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/income")
@RestController
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<IncomeResponse>> allUserIncomeSources(@RequestParam UUID userId) {
        List<IncomeResponse> incomeSources = incomeService.getIncomeSources(userId);
        return ResponseEntity.ok(incomeSources);
    }
}
