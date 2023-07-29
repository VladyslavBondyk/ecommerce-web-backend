package com.ecommercebackend.api.model;

public class LoginResponse {

    private String jwt;

    private boolean success;
    private String failureReason;

    public String getJwt() {
        return jwt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;


    }
}
