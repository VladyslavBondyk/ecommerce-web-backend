package com.ecommercebackend.api.model;

public class LoginResponse {

    private String jwt;

    private boolean success;
    private String failreReason;

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
        return failreReason;
    }

    public void setFailureReason(String failureReason) {
        this.failreReason = failreReason;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;


    }
}
