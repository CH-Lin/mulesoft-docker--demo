package com.employee.app.dao;

import com.employee.common.BaseDao;
import com.employee.common.EmployeeExistException;
import com.employee.common.EmployeeNotFoundException;
import com.employee.domain.model.Employee;

import java.util.List;

public interface EmployeeDao extends BaseDao<Employee> {

    public void addEmployee(Employee employee) throws EmployeeExistException;

    public Employee getEmployee(String name, String email) throws EmployeeNotFoundException;

    public List<Employee> getAllEmployee();

    public void updateEmployee(Employee employee);

    public int cleanup();

}
