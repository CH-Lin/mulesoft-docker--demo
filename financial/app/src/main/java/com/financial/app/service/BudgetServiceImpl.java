package com.financial.app.service;

import com.financial.app.dao.BudgetDao;
import com.financial.common.exception.BudgetExistException;
import com.financial.common.exception.BudgetNotEnoughException;
import com.financial.common.exception.BudgetNotFoundException;
import com.financial.domain.model.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired(required = true)
    public BudgetDao budgetDao;

    public void addBudget(int year, long amount) throws BudgetExistException {
        Budget budget = new Budget(year, amount);
        budgetDao.addBudget(budget);
    }

    public void updateBudget(int year, long amount) throws BudgetNotFoundException {
        Budget budget = budgetDao.getBudget(year);
        budget.setAmount(amount);
        budgetDao.updateBudget(budget);
    }

    public Long getBudget() throws BudgetNotFoundException {
        List<Budget> budgets = budgetDao.getBudget();
        long amount = 0;
        for (Budget budget : budgets) {
            amount += budget.getAmount();
        }
        if (amount == 0) {
            throw new BudgetNotFoundException();
        }
        return amount;
    }

    public Long getBudget(int year) throws BudgetNotFoundException {
        return budgetDao.getBudget(year).getAmount();
    }

    public synchronized void payment(int year, long amount) throws BudgetNotFoundException, BudgetNotEnoughException {
        Budget budget = budgetDao.getBudget(year);
        if ((budget.getAmount() - amount) <= 0) {
            throw new BudgetNotEnoughException();
        }
        budget.setAmount(budget.getAmount() - amount);
        budgetDao.updateBudget(budget);
    }

}
