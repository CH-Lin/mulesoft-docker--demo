package com.employee.common.reply;

import lombok.Getter;
import lombok.Setter;

public class ReimbursementInfoResponse {

    @Getter
    @Setter
    private int year;

    @Getter
    @Setter
    private int month;

    @Getter
    @Setter
    private String employeeName;

    @Getter
    @Setter
    private String employeeEmail;

    @Getter
    @Setter
    private int amount;
}
