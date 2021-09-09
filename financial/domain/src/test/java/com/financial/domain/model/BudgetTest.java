package com.financial.domain.model;

import com.financial.domain.DatabaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest extends DatabaseUnitTest {

    private final int year = 2021;
    private final long amount = 10000000;

    @BeforeEach
    public void prepare() {
        Budget budget = new Budget();
        budget.setYear(year);
        budget.setAmount(amount);
        em.persist(budget);
    }

    @Test
    public void testLoad() {
        TypedQuery<Budget> query = em.createQuery("select b from Budget b", Budget.class);
        List<Budget> result = query.getResultList();
        assertEquals(1, result.size());
        assertEquals(year, result.get(0).getYear());
        assertEquals(amount, result.get(0).getAmount());
    }

    @Test
    public void testSave() throws Exception {
        Budget budget = new Budget(year + 1, amount);
        em.persist(budget);
        TypedQuery<Budget> query = em.createQuery("select b from Budget b", Budget.class);
        List<Budget> result = query.getResultList();
        assertEquals(2, result.size());
    }

}
