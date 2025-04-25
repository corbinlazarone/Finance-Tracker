package com.fintrackerapi.fintracker.components;

import com.fintrackerapi.fintracker.entities.Transaction;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    private TransactionConverter() {}

    public static TransactionResponse convertToTransactionResponse(Transaction transaction) {
        if (transaction == null) throw new RuntimeException("Transaction can not be null");

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
