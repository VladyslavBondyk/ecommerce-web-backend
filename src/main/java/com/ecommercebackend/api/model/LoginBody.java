package com.ecommercebackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginBody {

    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;

    @org.jetbrains.annotations.NotNull
    public String getEmail() { return email; }

    public void setEmail(@org.jetbrains.annotations.NotNull String email) { this.email = email; }

    @org.jetbrains.annotations.NotNull
    public String getPassword() { return password; }

    public void setPassword(@org.jetbrains.annotations.NotNull String password) { this.password = password; }

}
