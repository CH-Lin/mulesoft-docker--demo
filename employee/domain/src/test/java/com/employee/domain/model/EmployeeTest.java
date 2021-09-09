package com.employee.domain.model;

import com.employee.domain.DatabaseUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest extends DatabaseUnitTest {

    private final String employeeName = "John";

    private final String employeeEmail = "john@company.com";

    @BeforeEach
    public void prepare() {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeEmail(employeeEmail);
        em.persist(employee);
    }

    @Test
    public void testLoad() {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
        List<Employee> result = query.getResultList();
        assertEquals(1, result.size());
        assertTrue(employeeName.equals(result.get(0).getEmployeeName()));
        assertTrue(result.get(0).getEmployeeID() > 0);
    }

    @Test
    public void testSave() throws Exception {
        Employee api = new Employee("Smith", "Smith@company.com", 1000000L);
        em.persist(api);
        TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
        List<Employee> result = query.getResultList();
        assertEquals(2, result.size());
    }

    @Test
    public void testSaveNotNullOnName() throws Exception {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Employee api = new Employee(null, "test@company.com", 1000000L);
            em.persist(api);
            em.flush();
        });
    }
}