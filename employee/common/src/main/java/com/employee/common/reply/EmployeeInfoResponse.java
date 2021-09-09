package com.employee.common.reply;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class EmployeeInfoResponse {

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

}
