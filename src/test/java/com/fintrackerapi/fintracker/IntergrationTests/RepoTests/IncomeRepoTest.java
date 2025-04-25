package com.fintrackerapi.fintracker.IntergrationTests.RepoTests;

import com.fintrackerapi.fintracker.entities.Income;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.repositories.IncomeRepo;
import com.fintrackerapi.fintracker.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST CASES
 * - Test save method
 * - Test findByUserId method for existing user
 * - Test findByUserId method for non-existing user
 * - Test deleteById method
 */

@DataJpaTest
public class IncomeRepoTest {

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    @Transactional
    @Rollback
    public void testSaveIncomeSource() {

        Income newIncome = new Income();
        newIncome.setName("Test Income");
        newIncome.setAmount(BigDecimal.valueOf(2000));
        newIncome.setIsBiweekly(true);
        newIncome.setPaymentDateTwo(1);
        newIncome.setPaymentDateTwo(15);

        Income savedIncome = incomeRepo.save(newIncome);

        // Assert that the retrieved income source is not null
        assertNotNull(savedIncome);

        // Assert that retrieved income id is not null
        assertNotNull(savedIncome.getId());

        // Assert that the retrieved income details match
        assertEquals(newIncome.getName(), savedIncome.getName());
        assertEquals(newIncome.getAmount(), savedIncome.getAmount());
        assertEquals(newIncome.getIsBiWeekly(), savedIncome.getIsBiWeekly());
        assertEquals(newIncome.getPaymentDateOne(), savedIncome.getPaymentDateOne());
        assertEquals(newIncome.getPaymentDateTwo(), savedIncome.getPaymentDateTwo());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByUserIdUserFound() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setFullName("Test1 test");
        user.setPassword("testPassword");

        User savedUser = userRepo.save(user);

        Income newIncome = new Income();
        newIncome.setUser(user);
        newIncome.setName("Test Income");
        newIncome.setAmount(BigDecimal.valueOf(2000));
        newIncome.setIsBiweekly(true);
        newIncome.setPaymentDateOne(1);
        newIncome.setPaymentDateTwo(15);

        Income savedIncome = incomeRepo.save(newIncome);

        // Retrieve the list income source from the database using findByUserId
        List<Income> foundIncomes = incomeRepo.findByUserId(savedUser.getId());

        // Assert that income source is not empty and size is one
        assertFalse(foundIncomes.isEmpty());
        assertEquals(1, foundIncomes.size());

        // Assert that the retrieved income details match
        Income retrievedIncome = foundIncomes.get(0);
        assertEquals(savedIncome.getId(), retrievedIncome.getId());
        assertEquals(savedIncome.getName(), retrievedIncome.getName());
        assertEquals(savedIncome.getAmount(), retrievedIncome.getAmount());
        assertEquals(savedIncome.getIsBiWeekly(), retrievedIncome.getIsBiWeekly());
        assertEquals(savedIncome.getPaymentDateOne(), retrievedIncome.getPaymentDateOne());
        assertEquals(savedIncome.getPaymentDateTwo(), retrievedIncome.getPaymentDateTwo());
        assertEquals(savedIncome.getCreatedAt(), retrievedIncome.getCreatedAt());
        assertEquals(savedIncome.getUpdated_at(), retrievedIncome.getUpdated_at());

        // Assert that the user relationship matches
        assertNotNull(retrievedIncome.getUser());
        assertEquals(savedUser.getId(), retrievedIncome.getUser().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByUserIdNoIncomeFound() {
        List<Income> foundIncomes = incomeRepo.findByUserId(UUID.randomUUID());

        // Assert that no income source was found
        assertTrue(foundIncomes.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteById() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setFullName("Test1 test");
        user.setPassword("testPassword");

        User savedUser = userRepo.save(user);

        Income newIncome = new Income();
        newIncome.setUser(user);
        newIncome.setName("Test Income");
        newIncome.setAmount(BigDecimal.valueOf(2000));
        newIncome.setIsBiweekly(true);
        newIncome.setPaymentDateOne(1);
        newIncome.setPaymentDateTwo(15);

        Income savedIncome = incomeRepo.save(newIncome);

        incomeRepo.deleteById(savedIncome.getId());

        List<Income> foundIncomes = incomeRepo.findByUserId(savedUser.getId());

        // Assert that foundIncomes is empty
        assertTrue(foundIncomes.isEmpty());
    }
}
