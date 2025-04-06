package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.responses.IncomeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IncomeInterface {
    List<IncomeResponse> getIncomeSources(UUID userId);
    IncomeResponse createIncomeSource(IncomeDto incomeDto);
    IncomeResponse updateIncomeSource(UUID incomeSourceId, IncomeDto updatedIncomeSource);
    boolean deleteIncomeSource(UUID userId, UUID incomeSourceId);
}
