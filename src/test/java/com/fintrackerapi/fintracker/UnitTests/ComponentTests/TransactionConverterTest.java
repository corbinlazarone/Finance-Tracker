package com.fintrackerapi.fintracker.UnitTests.ComponentTests;

import com.fintrackerapi.fintracker.components.TransactionConverter;
import com.fintrackerapi.fintracker.entities.Transaction;
import com.fintrackerapi.fintracker.enums.TransactionType;
import com.fintrackerapi.fintracker.responses.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST CASES
 * - Test convert transaction entity to transaction response successfully
 * - Test convert transaction entity to transaction response failure
 */

@ExtendWith(MockitoExtension.class)
public class TransactionConverterTest {

    @Test
    public void convertToTransactionResponseSuccess() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setName("Test transaction");
        transaction.setAmount(BigDecimal.valueOf(50.27));
        transaction.setMemo("Test memo");
        transaction.setType(TransactionType.EXPENSE);
        transaction.setCreatedAt(new Date());
        transaction.setUpdatedAt(new Date());

        TransactionResponse convertedTransaction = TransactionConverter.convertToTransactionResponse(transaction);

        assertNotNull(convertedTransaction);
        assertEquals(transaction.getId(), convertedTransaction.getId());
        assertEquals(transaction.getName(), convertedTransaction.getName());
        assertEquals(transaction.getAmount(), convertedTransaction.getAmount());
        assertEquals(transaction.getMemo(), convertedTransaction.getMemo());
        assertEquals(transaction.getType(), convertedTransaction.getType());
        assertEquals(transaction.getCreatedAt(), convertedTransaction.getCreatedAt());
        assertEquals(transaction.getUpdatedAt(), convertedTransaction.getUpdatedAt());
    }

    @Test
    public void convertToTransactionResponseFailure() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           TransactionConverter.convertToTransactionResponse(null);
        });

        assertEquals("Transaction can not be null", exception.getMessage());
    }
}