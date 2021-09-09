package com.employee.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

import static com.employee.domain.model.Reimbursement.*;


@Table(name = TABLE_NAME, indexes = {@Index(name = ID_IDX, columnList = ID)})
@Entity
public class Reimbursement {

    public static final String TABLE_NAME = "Reimbursement";
    public static final String ID = "ReimbursementID";
    public static final String ID_IDX = "reimbursement_id_index";
    private static final String TABLE_SEQS = "reimbursement_id_seq";

    @Id
    @Getter
    // @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reimbursementID;

    @Getter
    @Setter
    @NotNull
    private String reimbursementTitle = null;

    @Getter
    @Setter
    @NotNull
    private Date submissionDate = null;

    @Getter
    @Setter
    @NotNull
    private Integer amount = null;

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employee = null;

    public Reimbursement() {
    }

    public Reimbursement(String reimbursementTitle, Date date, Integer amount, Employee employee) {
        this.reimbursementTitle = reimbursementTitle;
        this.submissionDate = date;
        this.amount = amount;
        this.employee = employee;
    }
}
