package com.employee.app.service;

import com.employee.app.DatabaseUnitTest;
import com.employee.app.dao.EmployeeDao;
import com.employee.app.dao.EmployeeDaoImpl;
import com.employee.app.dao.ReimbursementDao;
import com.employee.app.dao.ReimbursementDaoImpl;
import com.employee.common.Config;
import com.employee.common.EmployeeExistException;
import com.employee.common.EmployeeNotFoundException;
import com.employee.common.reply.EmployeeInfoResponse;
import com.employee.common.reply.ReimbursementInfoResponse;
import com.employee.common.request.ReimbursementItem;
import com.employee.domain.model.Employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest extends DatabaseUnitTest {

    private final String employeeName = "Emma";

    private final String employeeEmail = "emma@company.com";

    private final long salary = 500000;

    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    private ReimbursementDao reimbursementDao = new ReimbursementDaoImpl();

    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    @BeforeEach
    protected void setup() throws EmployeeExistException {
        employeeService.employeeDao = employeeDao;
        employeeService.reimbursementDao = reimbursementDao;
        Employee employee = createEmployee(employeeName, employeeEmail, salary);
        employeeService.addEmployee(employee);
    }

    private Employee createEmployee(String employeeName, String employeeEmail, Long salary) throws EmployeeExistException {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeEmail(employeeEmail);
        employee.setSalary(salary);
        employeeService.addEmployee(employee);
        return employee;
    }

    private void addReimbursement(String employeeName, String employeeEmail, String reimbursementTitle, int year, int month, int date, Integer amount) throws EmployeeNotFoundException {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, date);
        employeeService.addReimbursement(employeeName, employeeEmail, reimbursementTitle, c.getTime(), amount);
    }

    private void addReimbursements(final int year, final String name, final String email) throws EmployeeNotFoundException {
        addReimbursement(name, email, "Notebook PC", year, 6, 10, 30000);
        addReimbursement(employeeName, employeeEmail, "Printer", year, 6, 20, 10000);
        addReimbursement(employeeName, employeeEmail, "Monitor", year, 6, 20, 20000);
        addReimbursement(name, email, "Transportation expenses", year, 7, 10, 3000);
        addReimbursement(employeeName, employeeEmail, "Book", year, 9, 10, 2000);
    }

    @Test
    public void testAddEmployee() throws EmployeeExistException {
        final String name = "Smith";
        final String email = "smith@company.com";
        final long salary = 1100000;
        createEmployee(name, email, salary);
        List<EmployeeInfoResponse> results = employeeService.getAllEmployee();
        assertEquals(2, results.size());
    }

    @Test
    public void testAddDuplicateEmployee() {
        final String name = "Smith";
        final String email = "smith@company.com";
        final long salary = 1100000;
        try {
            createEmployee(name, email, salary);
        } catch (EmployeeExistException e) {
            e.printStackTrace();
        }
        Assertions.assertThrows(EmployeeExistException.class, () -> {
            createEmployee(name, email, salary);
        });
    }

    @Test
    public void testAddReimbursement() throws EmployeeNotFoundException {
        final int amount = 30000;
        addReimbursement(employeeName, employeeEmail, "Notebook PC", 2021, 7, 20, amount);
        Employee result = employeeService.getEmployee(employeeName, employeeEmail);
        assertEquals(1, result.getReimbursements().size());
        assertEquals(amount, result.getReimbursements().get(0).getAmount());
    }

    @Test
    public void testAddReimbursements() throws EmployeeNotFoundException {
        Calendar c = Calendar.getInstance();
        List<ReimbursementItem> items = new ArrayList() {
            {
                add(new ReimbursementItem("PC", c.getTime(), 30000));
                add(new ReimbursementItem("Book", c.getTime(), 2000));
                add(new ReimbursementItem("Cables", c.getTime(), 3500));
            }
        };
        employeeService.addReimbursement(employeeName, employeeEmail, items);
        Employee result = employeeService.getEmployee(employeeName, employeeEmail);
        assertEquals(3, result.getReimbursements().size());
    }

    @Test
    public void testAddReimbursementUsingIncorrectEmployeeName() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            addReimbursement(employeeName + "_", employeeEmail, "Notebook PC", 2021, 7, 20, 30000);
        });
    }

    @Test
    public void testGetReimbursementByNameAndYear() throws EmployeeNotFoundException, EmployeeExistException {
        final int year = 2021;
        final String name = "John";
        final String email = "john@company.com";
        final long salary = 1500000;
        createEmployee(name, email, salary);
        addReimbursements(year, name, email);
        List<ReimbursementInfoResponse> result = employeeService.getReimbursement(employeeName, employeeEmail, year, -1);
        assertEquals(3, result.size());
    }

    @Test
    public void testGetReimbursementByNameAndYearMonth() throws EmployeeNotFoundException, EmployeeExistException {
        final int year = 2021;
        final String name = "John";
        final String email = "john@company.com";
        final long salary = 1500000;
        createEmployee(name, email, salary);
        addReimbursements(year, name, email);
        List<ReimbursementInfoResponse> result = employeeService.getReimbursement(employeeName, employeeEmail, year, 6);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetReimbursementByNameAndYearMonthWithoutEmailMatching() throws EmployeeNotFoundException, EmployeeExistException {
        final int year = 2021;
        final String name = "John";
        final String email = "john@company.com";
        final long salary = 1500000;
        createEmployee(name, email, salary);
        addReimbursements(year, name, email);
        Config.setEmailMatching(true);
        List<ReimbursementInfoResponse> result = employeeService.getReimbursement(employeeName, employeeEmail + "_", year, 6);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetReimbursementByYear() throws EmployeeNotFoundException, EmployeeExistException {
        final int year = 2021;
        final String name = "John";
        final String email = "john@company.com";
        final long salary = 1500000;
        createEmployee(name, email, salary);
        addReimbursements(year, name, email);
        List<ReimbursementInfoResponse> result = employeeService.getReimbursement(null, null, year, -1);
        assertEquals(5, result.size());
    }

    @Test
    public void testGetReimbursementByYearMonth() throws EmployeeNotFoundException, EmployeeExistException {
        final int year = 2021;
        final String name = "John";
        final String email = "john@company.com";
        final long salary = 1500000;
        createEmployee(name, email, salary);
        addReimbursements(year, name, email);
        List<ReimbursementInfoResponse> result = employeeService.getReimbursement(null, null, year, 6);
        assertEquals(3, result.size());
    }

}