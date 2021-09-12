package com.financial.app.service;

import com.financial.app.DatabaseUnitTest;
import com.financial.app.dao.BudgetDao;
import com.financial.app.dao.BudgetDaoImpl;
import com.financial.common.exception.BudgetExistException;
import com.financial.common.exception.BudgetNotEnoughException;
import com.financial.common.exception.BudgetNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest extends DatabaseUnitTest {

    private BudgetDao budgetDao = new BudgetDaoImpl();

    private BudgetServiceImpl budgetService = new BudgetServiceImpl();

    @BeforeEach
    protected void setup() {
        budgetService.budgetDao = budgetDao;
    }

    @Test
    public void testAddAndGetBudget() throws BudgetNotFoundException, BudgetExistException {
        int year = 2021;
        long amount = 100000000;
        budgetService.addBudget(year, amount);
        long result = budgetService.getBudget(year);
        assertEquals(amount, result);
    }

    @Test
    public void testAddDuplicateBudget() {
        Assertions.assertThrows(BudgetExistException.class, () -> {
            int year = 2021;
            long amount = 100000000;
            budgetService.addBudget(year, amount);
            budgetService.addBudget(year, amount);
        });
    }

    @Test
    public void testAddAndGetBudgetForWholeYear() throws BudgetNotFoundException, BudgetExistException {
        long amount = 100000000;
        budgetService.addBudget(2020, amount);
        budgetService.addBudget(2021, amount);
        budgetService.addBudget(2022, amount);
        long result = budgetService.getBudget();
        assertEquals(amount * 3, result);
    }

    @Test
    public void testGetBudgetNotExist() {
        Assertions.assertThrows(BudgetNotFoundException.class, () -> {
            int year = 2021;
            int amount = 100000000;
            budgetService.addBudget(year, amount);
            long result = budgetService.getBudget(year - 1);
            assertEquals(amount, result);
        });
    }

    @Test
    public void testGetBudgetNotExistForWholeYear() {
        Assertions.assertThrows(BudgetNotFoundException.class, () -> {
            budgetService.getBudget();
        });
    }

    @Test
    public void testSubtractBudget() throws BudgetNotFoundException, BudgetExistException {
        int year = 2021;
        long amount = 1000000;
        budgetService.addBudget(year, amount);
        budgetService.subtractBudget(year, amount);
        long result = budgetService.getBudget(year);
        assertEquals(0, result);
    }

    @Test
    public void testPayment() throws BudgetNotFoundException, BudgetNotEnoughException, BudgetExistException {
        int year = 2021;
        long amount = 1000000;
        long payment = 500000;
        budgetService.addBudget(year, amount);
        budgetService.payment(year, payment);
        long result = budgetService.getBudget(year);
        assertEquals(amount - payment, result);
    }

    @Test
    public void testPaymentBudgetNotEnough() {
        Assertions.assertThrows(BudgetNotEnoughException.class, () -> {
            int year = 2021;
            long amount = 1000000;
            long payment = amount + 1;
            budgetService.addBudget(year, amount);
            budgetService.payment(year, payment);
        });
    }

    @Test
    public void testCleanup() {
        Assertions.assertThrows(BudgetNotFoundException.class, () -> {
            budgetService.addBudget(2020, 1000000);
            budgetService.addBudget(2021, 1000000);
            budgetService.cleanup();
            budgetService.getBudget();
        });
    }

}
