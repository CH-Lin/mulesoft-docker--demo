package com.employee.common.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddReimbursementRequest {

    private String employeeName;

    private String employeeEmail;

    private List<ReimbursementItem> items;

    public AddReimbursementRequest() {
    }

    public AddReimbursementRequest(String employeeName, String employeeEmail, List<ReimbursementItem> items) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.items = items;
    }

    @JsonProperty("name")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @JsonProperty("email")
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    @JsonProperty("items")
    public List<ReimbursementItem> getItems() {
        return items;
    }

    public void setItems(List<ReimbursementItem> items) {
        this.items = items;
    }

}
