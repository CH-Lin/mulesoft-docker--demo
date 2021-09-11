package com.financial.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateBudgetRequest {

    private Integer year;

    private Long amount;

    public UpdateBudgetRequest() {
    }

    public UpdateBudgetRequest(Integer year, Long amount) {
        this.year = year;
        this.amount = amount;
    }

    @JsonProperty("year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @JsonProperty("amount")
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
