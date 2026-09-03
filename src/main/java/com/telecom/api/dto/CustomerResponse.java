package com.telecom.api.dto;

public class CustomerResponse {

    private String name;
    private String mobileNumber;
    private String plan;
    private double dataBalance;
    private double talktimeBalance;

    public CustomerResponse() {
    }

    public CustomerResponse(
            String name,
            String mobileNumber,
            String plan,
            double dataBalance,
            double talktimeBalance) {

        this.name = name;
        this.mobileNumber = mobileNumber;
        this.plan = plan;
        this.dataBalance = dataBalance;
        this.talktimeBalance = talktimeBalance;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPlan() {
        return plan;
    }

    public double getDataBalance() {
        return dataBalance;
    }

    public double getTalktimeBalance() {
        return talktimeBalance;
    }
}