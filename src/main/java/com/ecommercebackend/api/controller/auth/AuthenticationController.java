package com.ecommercebackend.api.controller.auth;


//import org.springframework.web.bind.annotation.Mapping;
import com.ecommercebackend.api.model.RegistrationBody;
import com.ecommercebackend.exception.UserAlreadyExistsException;
import com.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;


    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    //Register a user
    // ResponseEntity allows us to edit HTTP response which being sent back
    // @Valid is validated email address to be written correctly.
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody); // that will register user for us.
            return ResponseEntity.ok().build(); // if we dont fail response and get ok(200command) -> build -> send
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //In case we fail – sent 409Conflict + build it. No server error.
        }
        // System.out.println(registrationBody.getUsername());  // Test & prove that we have working registerBody

    }

}
