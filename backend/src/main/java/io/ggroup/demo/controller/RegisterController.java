package io.ggroup.demo.controller;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.ggroup.demo.dto.RegisterRequest;
import io.ggroup.demo.dto.UserResponse;
import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/register")
@Tag(name = "Register API", description = "Public user registration")
public class RegisterController {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository,
                              CustomerRepository customerRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Register a new user", description = "Creates a user account and customer profile")
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, "Email is already in use"));
        }

        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            User savedUser = userRepository.save(user);

            Customer customer = new Customer();
            customer.setFirstname(request.getFirstname());
            customer.setLastname(request.getLastname());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setUser(savedUser);
            customerRepository.save(customer);

            UserResponse response = new UserResponse();
            response.setUserId(savedUser.getUserId());
            response.setEmail(savedUser.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Registration failed: " + e.getMessage()));
        }
    }
}
