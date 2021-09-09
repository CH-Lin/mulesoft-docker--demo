package com.employee.common.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReimbursementItem {

    private String reimbursementTitle;

    private Date submissionDate;

    private Integer amount;

    public ReimbursementItem() {
    }

    public ReimbursementItem(String reimbursementTitle, Date submissionDate, Integer amount) {
        this.reimbursementTitle = reimbursementTitle;
        this.submissionDate = submissionDate;
        this.amount = amount;
    }

    @JsonProperty("title")
    public String getReimbursementTitle() {
        return reimbursementTitle;
    }

    public void setReimbursementTitle(String reimbursementTitle) {
        this.reimbursementTitle = reimbursementTitle;
    }

    @JsonProperty("date")
    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ReimbursementItem{" +
                "reimbursementTitle='" + reimbursementTitle + '\'' +
                ", submissionDate=" + submissionDate +
                ", amount=" + amount +
                '}';
    }

}
