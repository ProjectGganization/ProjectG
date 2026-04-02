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
@RequestMapping("/api/issuedtickets")
@Tag(name = "IssuedTicket API", description = "Endpoints for managing issuedtickets")
public class IssuedTicketController {

    private final IssuedTicketRepository issuedTicketRepository;
    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;

    public IssuedTicketController(IssuedTicketRepository issuedTicketRepository, TicketRepository ticketRepository, OrderRepository orderRepository){
        this.issuedTicketRepository = issuedTicketRepository;
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
    }

    // Get all issuedTickets
    @Operation(summary = "Get all issued tickets", description = "Returns a list of all issued tickets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All issued tickets found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IssuedTicket.class))
        ),
        @ApiResponse(responseCode = "404", description = "No issued tickets found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> getAllIssuedTickets() {
        List<IssuedTicket> list = issuedTicketRepository.findAll();
        if (list.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No issued tickets found"));
        }
        return ResponseEntity.ok(list);
    }

    // Get issuedTicket by its ID
    @Operation(summary = "Get issued ticket by ID", description = "Returns a single issued ticket by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Issued ticket found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IssuedTicket.class))
        ),
        @ApiResponse(responseCode = "404", description = "Issued ticket not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getIssuedTicketById(@PathVariable Integer id) {
        return issuedTicketRepository.findById(id)
                .map(ticket -> ResponseEntity.ok((Object) ticket))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Issued ticket not found")));
    }

    // Create a new issued ticket
    @Operation(summary = "Create a new issued ticket", description = "Adds a new issued ticket to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Issued ticket created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = IssuedTicket.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid issued ticket data",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<?> createIssuedTicket(@RequestBody IssuedTicket issuedTicket) {

        ResponseEntity<?> validation = validateAndAttachForeignKeys(issuedTicket);
        if(validation != null){
            return validation;
        }

        try {
            IssuedTicket saved = issuedTicketRepository.save(issuedTicket);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid issued ticket data: " + e.getMessage()));
        }
    }

    // Update an existing IssuedTicket
    @Operation(summary = "Update an existing IssuedTicket", description = "Updates the details of existing issuedticket")
    @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "IssuedTicket updated succesfully", content = @Content (mediaType = "application/json", schema = @Schema(implementation = IssuedTicket.class))),
         @ApiResponse(responseCode = "400", description = "Invalid issuedTicket data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
         @ApiResponse(responseCode = "404", description = "IssuedTicket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIssuedticket(@PathVariable Integer id, @RequestBody IssuedTicket issuedTicket){
        if(!issuedTicketRepository.existsById(id)){
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(404, "IssuedTicket not found with ID: " + id));
        }

        ResponseEntity<?> validation = validateAndAttachForeignKeys(issuedTicket);
        if(validation != null){
            return validation;
        }

        try {
            issuedTicket.setIssuedTicketId(id);
            IssuedTicket updateIssuedticket = issuedTicketRepository.save(issuedTicket);
            return ResponseEntity.ok(updateIssuedticket);
        } catch (Exception e) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(400, "Invalid issuedTicket data: " + e.getMessage()));
        }
    }

    // OrderID and ticketID validation
    private ResponseEntity<?> validateAndAttachForeignKeys(IssuedTicket issuedTicket){

        if(issuedTicket.getTicket() == null){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(400, "Ticket is required"));
        }

        Integer ticketId = issuedTicket.getTicket().getTicketId();
        if (ticketId == null){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(400, "Ticket id is required"));
        }

        Ticket existingTicket = ticketRepository.findById(ticketId).orElse(null);
        if(existingTicket == null){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(404, "Ticket not found"));
        }

        issuedTicket.setTicket(existingTicket);

        if(issuedTicket.getOrder() == null){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(400, "Order is required"));
        }

        Integer orderId = issuedTicket.getOrder().getOrderId();
        if(orderId == null) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(400, "Order id is required"));
        }

        Order existingOrder = orderRepository.findById(orderId).orElse(null);
        if(existingOrder == null){
            return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(new ErrorResponse(400, "Order not found"));
        }

        issuedTicket.setOrder(existingOrder);
        return null;
    }

    // Delete IssuedTicket by ID
    @Operation(summary = "Delete existing IssuedTicket", description = "Delete existing IssuedTicket by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "IssuedTicket deleted succesfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted issued ticket with id {id}\"}"))),
        @ApiResponse(responseCode = "404", description = "IssuedTicket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssuedTicketById(@PathVariable Integer id){
        if (issuedTicketRepository.existsById(id)){
            issuedTicketRepository.deleteById(id);
             return ResponseEntity.ok(
                Map.of("message", "Successfully deleted issued ticket with id " + id) 
             );
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "IssuedTicket not found"));
        }
    }
}

