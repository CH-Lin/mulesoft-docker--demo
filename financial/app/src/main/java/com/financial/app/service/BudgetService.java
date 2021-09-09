package com.financial.app.service;

import com.financial.common.exception.BudgetExistException;
import com.financial.common.exception.BudgetNotEnoughException;
import com.financial.common.exception.BudgetNotFoundException;

public interface BudgetService {

    public void addBudget(int year, long amount) throws BudgetExistException;

    public void updateBudget(int year, long amount) throws BudgetNotFoundException;

    public Long getBudget() throws BudgetNotFoundException;

    public Long getBudget(int year) throws BudgetNotFoundException;

    public void payment(int year, long amount) throws BudgetNotFoundException, BudgetNotEnoughException;

}
