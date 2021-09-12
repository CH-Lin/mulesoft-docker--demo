package com.financial.app.dao;

import com.financial.common.BaseDaoImpl;
import com.financial.common.exception.BudgetExistException;
import com.financial.common.exception.BudgetNotFoundException;
import com.financial.domain.model.Budget;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BudgetDaoImpl extends BaseDaoImpl<Budget> implements BudgetDao {

    @Transactional
    public void addBudget(Budget budget) throws BudgetExistException {
        try {
            persistObject(budget);
        } catch (Exception e) {
            throw new BudgetExistException();
        }
    }

    @Transactional
    public void updateBudget(Budget budget) {
        mergeObject(budget);
    }

    @Transactional
    public List<Budget> getBudget() {
        TypedQuery<Budget> query = getEntityManager().createQuery("SELECT b FROM Budget b",
                Budget.class);
        return query.getResultList();
    }

    @Transactional
    public Budget getBudget(int year) throws BudgetNotFoundException {
        try {
            TypedQuery<Budget> query = getEntityManager().createQuery("SELECT b FROM Budget b WHERE b.year = :year",
                    Budget.class);
            return query.setParameter("year", year).getSingleResult();
        } catch (NoResultException e) {
            throw new BudgetNotFoundException();
        }
    }

    @Transactional
    public int cleanup() {
        int deletedCount = getEntityManager().createQuery("DELETE FROM Budget").executeUpdate();
        return deletedCount;
    }

}
