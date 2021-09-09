package com.employee.domain.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.employee.domain.model.Employee.*;


@Table(name = TABLE_NAME, indexes = {@Index(name = EMPLOYEE_ID_IDX, columnList = EMPLOYEE_ID)},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeName", "employeeEmail"})})
@Entity
public class Employee {

    public static final String TABLE_NAME = "Employee";
    public static final String EMPLOYEE_ID = "EmployeeID";
    public static final String EMPLOYEE_ID_IDX = "employee_id_index";

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private long employeeID;

    @Getter
    @Setter
    @NotNull
    private String employeeName = null;

    @Getter
    @Setter
    @NotNull
    private String employeeEmail = null;

    @Getter
    @Setter
    private Long salary;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Reimbursement> reimbursements;

    public Employee() {
    }

    public Employee(String employeeName, String employeeEmail, Long salary) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.salary = salary;
        reimbursements = new ArrayList<>();
    }

    public void addReimbursement(Reimbursement reimbursement) {
        if (reimbursements == null) {
            reimbursements = new ArrayList<>();
        }
        reimbursements.add(reimbursement);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", salary=" + salary +
                ", reimbursements=" + reimbursements +
                '}';
    }
}