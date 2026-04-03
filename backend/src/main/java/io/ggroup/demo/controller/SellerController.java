package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/sellers")
@Tag(name = "Seller api", description = "Endpoints for managing sellers")
public class SellerController {
    
    private final SellerRepository sellerRepository;

    public SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    // GET /api/sellers - Get all sellers
    
    @Operation(summary = "Get all sellers", description = "Returns a list of all sellers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sellers found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class))),
            @ApiResponse(responseCode = "404", description = "No sellers found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

     @GetMapping
    public ResponseEntity<?> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();

        if (sellers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No sellers found"));
        } else {
            return ResponseEntity.ok(sellers);
        }
    }


     // GET /api/sellers/{id} - Get seller by ID

    @Operation(summary = "Get seller by ID", description = "Returns a single seller by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class))),
            @ApiResponse(responseCode = "404", description = "Seller not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

   

    @GetMapping("/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable Integer id) {
    return sellerRepository.findById(id)
            .map(seller -> ResponseEntity.ok((Object) seller))
            .orElseGet(() -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Seller not found with ID: " + id)));
}
    

    // POST /api/sellers - Create a new seller

    @Operation(summary = "Create a new seller", description = "Creates a new seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seller created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })

    @PostMapping
     public ResponseEntity<?> createSeller(@RequestBody Seller seller) {
        try {
            Seller savedSeller = sellerRepository.save(seller);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSeller);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid seller data: " + e.getMessage()));
        }
    }

    // PUT /api/sellers/{id} - Update an existing seller

    @Operation(summary = "Update an existing seller", description = "Updates the details of an existing seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class))),
            @ApiResponse(responseCode = "400", description = "Invalid seller data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Seller not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(@PathVariable Integer id, @RequestBody Seller seller) {
        Seller existingSeller = sellerRepository.findById(id).orElse(null);
        if (existingSeller == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Seller not found with ID: " + id));
        }

        if (seller.getName() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Seller name is required"));
        }
        
        if (seller.getEmail() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Seller email is required"));
        }

        existingSeller.setName(seller.getName());
        existingSeller.setEmail(seller.getEmail());
        existingSeller.setPhone(seller.getPhone());
        existingSeller.setUser(seller.getUser());

        Seller updatedSeller = sellerRepository.save(existingSeller);
        return ResponseEntity.ok(updatedSeller);
    }

            
    // DELETE /api/sellers/{id} - Delete seller by ID
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a seller", description = "Deletes a seller by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Seller deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted seller with id {id}\"}"))),
        @ApiResponse(responseCode = "404", description = "Seller not found",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)))
})
    public ResponseEntity<?> deleteSellerById(@PathVariable Integer id) {

    if (sellerRepository.existsById(id)) { 
        try {
            sellerRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Successfully deleted seller with id " + id)); 
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Cannot delete seller"));
        }
    } else {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Seller not found"));
    }
}

}

