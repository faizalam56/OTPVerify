package com.athira.otpverify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOTPResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
