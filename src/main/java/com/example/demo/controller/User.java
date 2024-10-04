package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.response.LoginResponse;

@RestController
@RequestMapping("/user")
public class User {
    @Autowired
    private UserService userService;

    @PostMapping("/new-user")
    public ResponseEntity<String> addUser(@RequestBody UserModel content) {
        Boolean user = userService.addNewUser(content);
        if (user) {
            String message = "User created successfully!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String errorMessage = "User already exist.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/log-in")
    public ResponseEntity<LoginResponse> logInUser(@RequestBody UserModel content) {
        String token = userService.signInUser(content);
        if (token != "Password is incorrect") {
            String message = "User logged in successfully!";
            LoginResponse response = new LoginResponse(token, message);
            return ResponseEntity.ok(response);
        } else {
            String errorMessage = "Failed to find user.";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, errorMessage));
        }
    }

}
