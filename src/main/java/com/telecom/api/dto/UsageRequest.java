package com.telecom.api.dto;

public class UsageRequest {

    private Double dataUsed;
    private Double talktimeUsed;

    public Double getDataUsed() {
        return dataUsed;
    }

    public void setDataUsed(Double dataUsed) {
        this.dataUsed = dataUsed;
    }

    public Double getTalktimeUsed() {
        return talktimeUsed;
    }

    public void setTalktimeUsed(Double talktimeUsed) {
        this.talktimeUsed = talktimeUsed;
    }
}