package com.telecom.api.dto;

public class RechargeRequest {

    private Long planId;

    public RechargeRequest() {
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}