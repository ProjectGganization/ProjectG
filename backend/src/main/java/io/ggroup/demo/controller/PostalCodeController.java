package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/postalcodes")
@Tag(name = "Postal Code API", description = "Endpoints for managing postal codes")
public class PostalCodeController {

    private final PostalCodeRepository postalCodeRepository;

    public PostalCodeController(PostalCodeRepository postalCodeRepository) {
        this.postalCodeRepository = postalCodeRepository;
    }

    @Operation(summary = "Get all postal codes", description = "Returns a list of all postal codes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All postal codes found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostalCode.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No postal codes found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<?> getAllPostalCodes() {
        List<PostalCode> postalCodes = postalCodeRepository.findAll();
        if (postalCodes.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No postal codes found"));
        }
        return ResponseEntity.ok(postalCodes);
    }

    @Operation(summary = "Get postal code by ID", description = "Returns a single postal code by its postal code value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postal code found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostalCode.class))),
            @ApiResponse(responseCode = "404", description = "Postal code not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{postalCode}")
    public ResponseEntity<?> getPostalCodeById(@PathVariable String postalCode) {
        return postalCodeRepository.findById(postalCode)
                .map(code -> ResponseEntity.ok((Object) code))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Postal code not found")));
    }

    @Operation(summary = "Create a new postal code", description = "Adds a new postal code to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Postal code created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostalCode.class))),
            @ApiResponse(responseCode = "400", description = "Invalid postal code data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createPostalCode(@RequestBody PostalCode postalCode) {
        try {
            PostalCode savedPostalCode = postalCodeRepository.save(postalCode);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPostalCode);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid postal code data: " + e.getMessage()));
        }
    }

    @Operation(summary = "Update an existing postal code", description = "Updates city information for an existing postal code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postal code updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostalCode.class))),
            @ApiResponse(responseCode = "400", description = "Invalid postal code data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Postal code not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{postalCode}")
    public ResponseEntity<?> updatePostalCode(@PathVariable String postalCode, @RequestBody PostalCode body) {
        if (!postalCodeRepository.existsById(postalCode)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Postal code not found"));
        }
        try {
            body.setPostalCode(postalCode);
            PostalCode updatedPostalCode = postalCodeRepository.save(body);
            return ResponseEntity.ok(updatedPostalCode);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid postal code data: " + e.getMessage()));
        }
    }

    @Operation(summary = "Delete postal code by ID", description = "Deletes a single postal code by its value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Postal code deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted postal code with id {id}\"}"))),
            @ApiResponse(responseCode = "400", description = "Postal code is in use", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Postal code not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{postalCode}")
    public ResponseEntity<?> deletePostalCodeById(@PathVariable String postalCode) {
        if (!postalCodeRepository.existsById(postalCode)) {
            return ResponseEntity 
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Postal code not found"));
        }

        try {
            postalCodeRepository.deleteById(postalCode);
            return ResponseEntity.ok(Map.of("message", "Successfully deleted postal code with id " + postalCode));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Postal code is in use and cannot be deleted"));
        }
    }
}
