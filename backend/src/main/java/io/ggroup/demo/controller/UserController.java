package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;
import io.ggroup.demo.dto.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Endpoints for managing users")
public class UserController {

    private final UserRepository userRepository;
    private final AccountStatusRepository statusRepository;

    public UserController(UserRepository userRepository, AccountStatusRepository statusRepository) {
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
    }

    // POST /api/users - Create a new user
    @Operation(summary = "Create a new user", description = "Adds a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        // DTO validation
        try {
            User newUser = new User();
            newUser.setEmail(request.getEmail());
            // newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            // (MUUTA TÄMÄ KUN SECURITYCONFIG TEHTY)
            newUser.setPasswordHash("HASH_" + request.getPassword()); // HOX!! TILAPÄINEN RATKAISU, POISTA KUN
                                                                      // SECURITYCONFIG TEHTY
            if (request.getAccountStatus() != null) {
                statusRepository.findById(request.getAccountStatus()).ifPresent(newUser::setAccountStatus);
            }

            User savedUser = userRepository.save(newUser);

            // Map entity to response DTO
            UserResponse response = new UserResponse();
            response.setUserId(savedUser.getUserId());
            response.setEmail(savedUser.getEmail());
            if (newUser.getAccountStatus() != null) {
                response.setStatusName(newUser.getAccountStatus().getAccountStatus());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "invalid user data: " + e.getMessage()));
        }

    }

    // GET /api/users/{id} - Get user by ID
    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {

        try {
            var opt = userRepository.findById(id);

            if (opt.isPresent()) {
                User user = opt.get();

                // Map entity to response DTO
                UserResponse response = new UserResponse();
                response.setUserId(user.getUserId());
                response.setEmail(user.getEmail());
                if (user.getAccountStatus() != null) {
                    response.setStatusName(user.getAccountStatus().getAccountStatus());
                }
                return ResponseEntity.ok(response);
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "User not found with ID " + id));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Error occurred while fetching user: " + e.getMessage()));
        }
    }

    // GET /api/users - Get all users
    @Operation(summary = "Get all users", description = "Returns a list of all users in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "No users found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No users found"));
        }

        List<UserResponse> responseList = users.stream().map(user -> {
            UserResponse res = new UserResponse();
            res.setUserId(user.getUserId());
            res.setEmail(user.getEmail());
            if (user.getAccountStatus() != null) {
                res.setStatusName(user.getAccountStatus().getAccountStatus());
            }
            return res;
        }).toList();

        return ResponseEntity.ok(responseList);
    }

    // DELETE /api/users/{id} - Delete user by ID
    @Operation(summary = "Delete user by ID", description = "Deletes a user from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted user with id {id}\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "User not found"));
        }
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Successfully deleted user with id " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Delete failed: User may be linked to other data."));
        }
    }

}
