package com.example.shopapp.controllers;

import com.example.shopapp.dtos.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shopapp.dtos.*;
@RestController
@RequestMapping
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<?> CreateUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.ok().body("User logged in successfully");
    }
}
