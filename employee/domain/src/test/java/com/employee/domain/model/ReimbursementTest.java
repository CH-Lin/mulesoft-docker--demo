package com.employee.domain.model;

import com.employee.domain.DatabaseUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementTest extends DatabaseUnitTest {

    private final String employeeName = "John";

    private final String employeeEmail = "john@company.com";

    private final String reimbursementTitle = "Notebook PC";

    @BeforeEach
    public void prepare() {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeEmail(employeeEmail);
        em.persist(employee);
        Calendar c = Calendar.getInstance();
        c.set(2021, 7, 20);
        Reimbursement reimbursement = new Reimbursement("Notebook PC", c.getTime(), 3000, employee);
        employee.addReimbursement(reimbursement);
        em.persist(reimbursement);
        em.persist(employee);
    }

    @Test
    public void testLoad() {
        TypedQuery<Reimbursement> query = em.createQuery("select r from Reimbursement r", Reimbursement.class);
        List<Reimbursement> result = query.getResultList();
        assertEquals(1, result.size());
        assertTrue(employeeName.equals(result.get(0).getEmployee().getEmployeeName()));
        assertTrue(reimbursementTitle.equals(result.get(0).getReimbursementTitle()));
    }

    @Test
    public void testSave() throws Exception {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.employeeName = :employeeName", Employee.class);
        query.setParameter("employeeName", employeeName);
        Employee employee = query.getSingleResult();
        Calendar c = Calendar.getInstance();
        c.set(2021, 7, 25);
        Reimbursement reimbursement = new Reimbursement("Book", c.getTime(), 3000, employee);
        employee.addReimbursement(reimbursement);
        em.persist(reimbursement);
        em.persist(employee);
        TypedQuery<Reimbursement> query2 = em.createQuery("select r from Reimbursement r", Reimbursement.class);
        List<Reimbursement> result = query2.getResultList();
        assertEquals(2, result.size());
    }

    @Test
    public void testSaveNotNullOnName() throws Exception {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Employee api = new Employee(null, "test@company.com", 10L);
            em.persist(api);
            em.flush();
        });
    }
}