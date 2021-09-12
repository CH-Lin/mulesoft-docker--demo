package com.employee.app.service;

import com.employee.app.dao.EmployeeDao;
import com.employee.app.dao.ReimbursementDao;
import com.employee.common.EmployeeExistException;
import com.employee.common.EmployeeNotFoundException;
import com.employee.common.reply.EmployeeInfoResponse;
import com.employee.common.reply.ReimbursementInfoResponse;
import com.employee.common.request.ReimbursementItem;
import com.employee.domain.model.Employee;
import com.employee.domain.model.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired(required = true)
    public EmployeeDao employeeDao;

    @Autowired(required = true)
    public ReimbursementDao reimbursementDao;

    public void addEmployee(Employee employee) throws EmployeeExistException {
        employeeDao.addEmployee(employee);
    }

    public Employee getEmployee(String name, String email) throws EmployeeNotFoundException {
        return employeeDao.getEmployee(name, email);
    }

    public List<EmployeeInfoResponse> getAllEmployee() {
        List<EmployeeInfoResponse> result = new ArrayList<>();
        for (Employee e : employeeDao.getAllEmployee()) {
            EmployeeInfoResponse response = new EmployeeInfoResponse();
            response.setEmployeeName(e.getEmployeeName());
            response.setEmployeeEmail(e.getEmployeeEmail());
            response.setSalary(e.getSalary());
            result.add(response);
        }
        return result;
    }

    public void addReimbursement(String employeeName, String email, String reimbursementTitle, Date submissionDate, Integer amount) throws EmployeeNotFoundException {
        Employee employee = employeeDao.getEmployee(employeeName, email);

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setReimbursementTitle(reimbursementTitle);
        reimbursement.setSubmissionDate(submissionDate);
        reimbursement.setAmount(amount);
        reimbursement.setEmployee(employee);

        employee.addReimbursement(reimbursement);

        reimbursementDao.addReimbursement(reimbursement);
        employeeDao.updateEmployee(employee);
    }

    public void addReimbursement(String name, String email, List<ReimbursementItem> items) throws EmployeeNotFoundException {
        for (ReimbursementItem item : items) {
            addReimbursement(name, email, item.getReimbursementTitle(), item.getSubmissionDate(), item.getAmount());
        }
    }

    private List<ReimbursementInfoResponse> convertToResponse(List<Reimbursement> reimbursements) {
        List<ReimbursementInfoResponse> response = new ArrayList<>();
        for (Reimbursement reimbursement : reimbursements) {
            ReimbursementInfoResponse info = new ReimbursementInfoResponse();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reimbursement.getSubmissionDate());
            info.setYear(cal.get(Calendar.YEAR));
            info.setMonth(cal.get(Calendar.MONTH) + 1);
            info.setAmount(reimbursement.getAmount());
            info.setEmployeeName(reimbursement.getEmployee().getEmployeeName());
            info.setEmployeeEmail(reimbursement.getEmployee().getEmployeeEmail());
            response.add(info);
        }
        return response;
    }

    public List<ReimbursementInfoResponse> getReimbursement(String name, String email, int year, int month) {
        return convertToResponse(reimbursementDao.getReimbursement(name, email, year, (month - 1)));
    }

    public int cleanup() {
        return reimbursementDao.cleanup() + employeeDao.cleanup();
    }

}
