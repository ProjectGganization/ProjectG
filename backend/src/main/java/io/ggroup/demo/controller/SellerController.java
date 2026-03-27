package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ggroup.demo.model.ErrorResponse;
import io.ggroup.demo.model.Event;
import io.ggroup.demo.model.Seller;
import io.ggroup.demo.repository.EventRepository;
import io.ggroup.demo.repository.SellerRepository;
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
    private final EventRepository eventRepository;

    public SellerController(SellerRepository sellerRepository, EventRepository eventRepository) {
        this.sellerRepository = sellerRepository;
        this.eventRepository = eventRepository;
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
        ResponseEntity<?> eventValidation = validateAndAttachSellerSeller(seller);
        if (eventValidation != null) {
            return eventValidation;
        }
        try {
            Seller savedSeller = sellerRepository.save(seller);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSeller);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid ticket data: " + e.getMessage()));
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
    public ResponseEntity<?> updateSeller(@PathVariable Integer id, @RequestBody Seller seller)
        {
            if (!sellerRepository.existsById(id)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Seller not found with ID: " + id));
            }
    
             try {
            seller.setSellerId(id);
            Seller updatedSeller = sellerRepository.save(seller);
            return ResponseEntity.ok(updatedSeller);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, e.getMessage()));
        }
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

// SellerIDValidation
    private ResponseEntity<?> validateAndAttachSellerSeller(Seller seller) {
        if (seller.getEvent() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Event is required"));
        }

        Integer eventId = ((Event) seller.getEvent()).getEventId();
        if (eventId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Event id is required"));
        }

        Event existingEvent = eventRepository.findById(eventId).orElse(null);
        if (existingEvent == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Event with id " + eventId + " does not exist"));
        }

        seller.setEvent(existingEvent);
        return null;
    }

}
