package com.employee.app.service;

import com.employee.common.EmployeeExistException;
import com.employee.common.EmployeeNotFoundException;
import com.employee.common.reply.EmployeeInfoResponse;
import com.employee.common.reply.ReimbursementInfoResponse;
import com.employee.common.request.ReimbursementItem;
import com.employee.domain.model.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeeService {

    public void addEmployee(Employee employee) throws EmployeeExistException;

    public Employee getEmployee(String name, String email) throws EmployeeNotFoundException;

    public List<EmployeeInfoResponse> getAllEmployee();

    public void addReimbursement(String name, String email, String reimbursementTitle, Date submissionDate, Integer amount) throws EmployeeNotFoundException;

    public void addReimbursement(String name, String email, List<ReimbursementItem> items) throws EmployeeNotFoundException;

    public List<ReimbursementInfoResponse> getReimbursement(String name, String email, int year, int month);

}
