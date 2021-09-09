package com.financial.app.dao;

import com.financial.common.BaseDao;
import com.financial.common.exception.BudgetExistException;
import com.financial.common.exception.BudgetNotFoundException;
import com.financial.domain.model.Budget;

import java.util.List;

public interface BudgetDao extends BaseDao<Budget> {

    public void addBudget(Budget budget) throws BudgetExistException;

    public void updateBudget(Budget budget);

    public List<Budget> getBudget();

    public Budget getBudget(int year) throws BudgetNotFoundException;

}
