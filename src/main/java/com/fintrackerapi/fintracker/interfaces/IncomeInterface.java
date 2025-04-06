package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IncomeInterface {
    List<IncomeResponse> getIncomeSources(UUID userId);
    Boolean createIncomeSource(IncomeDto incomeDto);
}
