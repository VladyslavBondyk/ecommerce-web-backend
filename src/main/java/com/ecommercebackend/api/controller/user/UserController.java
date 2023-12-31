package com.ecommercebackend.api.controller.user;

import com.ecommercebackend.model.Address;
import com.ecommercebackend.model.LocalUser;
import com.ecommercebackend.model.dao.AddressDAO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private AddressDAO addressDAO;


    public UserController(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;

    }
// adding a property in curly braces
    @GetMapping("/{userId}/address")
    @Operation(summary = "Get the user's address")
    public ResponseEntity<List<Address>> getAddress(
           @AuthenticationPrincipal LocalUser user, @PathVariable Long userId) {
        if (!userHasPermission(user, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(addressDAO.findByUser_Id(userId));
    }

    @PutMapping("/{userId}/address")
    @Operation(summary = "Add new address to the user")
    public ResponseEntity<Address> putAddress (
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @RequestBody Address address) {
        //checking if user has permission
        if (!userHasPermission(user, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        //setting the id address to null
        address.setId(null);
        //create a Dummy user
        LocalUser refUser = new LocalUser();
        // set if ti that user
        refUser.setId(userId);
        // sign that dummy user to the address
        address.setUser(user);
        // save it ti DB
        return ResponseEntity.ok(addressDAO.save(address));
    }

    @PatchMapping("/{userId}/address/{addressId}")
    @Operation(summary = "Change the data in choosen address")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Address address) {
        if (!userHasPermission(user, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressDAO.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getUser();
                if (originalUser.getId() == userId){
                    address.setUser(originalUser);
                    return ResponseEntity.ok(addressDAO.save(address));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean userHasPermission(LocalUser user, Long id) {
        return Objects.equals(user.getId(), id);
    }
}
