package com.ecommercebackend.api.model;

//import jakarta.validation.constraints.*;
import jakarta.validation.constraints.*;

public class RegistrationBody {

    // Fields for registration. Align with FE reg this.

//    private String username;

    @NotNull
    @NotBlank
    @Email   //Spring supply us with this validation
    private String email;
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    @Size(min = 8, max = 32)
    // Max/Min uses only for Integers
    private String password;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    public RegistrationBody() {
    }

//    public String getUsername() {
//        return username;
//    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
