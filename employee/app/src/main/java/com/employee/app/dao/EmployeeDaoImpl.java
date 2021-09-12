package com.employee.app.dao;

import com.employee.common.BaseDaoImpl;
import com.employee.common.Config;
import com.employee.common.EmployeeExistException;
import com.employee.common.EmployeeNotFoundException;
import com.employee.domain.model.Employee;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {

    @Transactional
    public void addEmployee(Employee employee) throws EmployeeExistException {
        try {
            persistObject(employee);
        } catch (Exception e) {
            throw new EmployeeExistException();
        }
    }

    @Transactional
    public Employee getEmployee(String name, String email) throws EmployeeNotFoundException {
        try {
            if (Config.isEmailMatching()) {
                TypedQuery<Employee> query = getEntityManager().createQuery("SELECT e FROM Employee e WHERE e.employeeName = :employeeName AND e.employeeEmail = :employeeEmail",
                        Employee.class);
                return query.setParameter("employeeName", name).setParameter("employeeEmail", email).getSingleResult();
            } else {
                TypedQuery<Employee> query = getEntityManager().createQuery("SELECT e FROM Employee e WHERE e.employeeName = :employeeName",
                        Employee.class);
                return query.setParameter("employeeName", name).getSingleResult();
            }
        } catch (NoResultException e) {
            throw new EmployeeNotFoundException();
        }
    }

    public List<Employee> getAllEmployee() {
        TypedQuery<Employee> query = getEntityManager().createQuery("SELECT e FROM Employee e",
                Employee.class);
        return query.getResultList();
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        mergeObject(employee);
    }

    @Transactional
    public int cleanup() {
        int deletedCount = getEntityManager().createQuery("DELETE FROM Employee").executeUpdate();
        return deletedCount;
    }

}
