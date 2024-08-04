package com.cashrich.userprofile_service.controller;


import com.cashrich.userprofile_service.model.UserRequest;
import com.cashrich.userprofile_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRequest> registerUser(@Valid @RequestBody UserRequest user) {
        try {
            UserRequest registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestParam String username, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-Origin", "YourAPIOrigin");
        if (isAuthenticated) {
            return new ResponseEntity<>("Authentication successful", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Authentication failed", headers, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserRequest> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest updatedUser) {
        try {
            UserRequest updated = userService.updateUser(id, updatedUser);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserRequest> getUserByUsername(@PathVariable String username) {
        try {
            UserRequest user = userService.getUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/coin-data")
    public ResponseEntity<String> getCryptoData(@RequestParam String userId) {
        try {
            String response = userService.fetchCoinData(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
