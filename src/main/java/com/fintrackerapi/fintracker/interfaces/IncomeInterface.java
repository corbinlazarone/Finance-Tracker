package com.fintrackerapi.fintracker.interfaces;

import com.fintrackerapi.fintracker.dtos.IncomeDto;
import com.fintrackerapi.fintracker.responses.IncomeResponse;

import java.util.List;
import java.util.UUID;

public interface IncomeInterface {
    List<IncomeResponse> getIncomeSources(UUID userId);
    IncomeResponse createIncomeSource(UUID userId, IncomeDto incomeDto);
    IncomeResponse updateIncomeSource(UUID userId, UUID incomeSourceId, IncomeDto updatedIncomeSource);
    boolean deleteIncomeSource(UUID userId, UUID incomeSourceId);
}
