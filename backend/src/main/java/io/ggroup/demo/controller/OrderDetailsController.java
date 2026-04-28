package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.ggroup.demo.model.*;

import io.ggroup.demo.repository.OrderDetailsRepository;
import io.ggroup.demo.repository.TicketRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orderdetails")
@Tag(name="OrderDetails API", description="Endpoints for managing order details") 

public class OrderDetailsController {

private final OrderDetailsRepository orderDetailsRepository;
private final TicketRepository ticketRepository;

    public OrderDetailsController(OrderDetailsRepository orderDetailsRepository, TicketRepository ticketRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.ticketRepository = ticketRepository;
    }

    // GET /api/orderdetails - Get all order details
    
    @Operation(summary = "get all order details", description = "Returns a list of all order details")
    @ApiResponses(value = {
       @ApiResponse(
            responseCode = "200",
            description = "All order details found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetails.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No order details found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> getAllOrderDetails() {
        List<OrderDetails> details = orderDetailsRepository.findAll();
        if (details.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No order details found"));
        } else {
            return ResponseEntity.ok(details);
        }
    }

     // GET /api/orderdetails/{id} - Get order details by ID
    
    @Operation(summary = "Get order detail by ID", description = "Returns a single order detail by orderId and ticketId")
    @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Order detail found successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetails.class))
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Order detail not found",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    })
    
    @GetMapping("/{orderId}/{ticketId}")
    public ResponseEntity<?> getOrderDetailById(
        @PathVariable Integer orderId,
        @PathVariable Integer ticketId) {

    OrderDetails.OrderDetailsId id = new OrderDetails.OrderDetailsId(orderId, ticketId);

    return orderDetailsRepository.findById(id)
            .map(orderDetails -> ResponseEntity.ok((Object) orderDetails))
            .orElseGet(() -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Order detail not found")));
}



    // POST /api/orderdetails - Create a new order detail
    @Operation(summary = "Create a new order detail", description = "Creates a new order detail for an order")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Order detail created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetails.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetails orderDetails) {
        Integer ticketId = orderDetails.getId().getTicketId();
        int quantity = orderDetails.getQuantity();

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Ticket not found: " + ticketId));
        }
        if (ticket.getInStock() < quantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Not enough tickets in stock"));
        }

        ticket.setInStock(ticket.getInStock() - quantity);
        ticketRepository.save(ticket);

        OrderDetails saved = orderDetailsRepository.save(orderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    // UPDATE /api/orders/{id} - Update an existing order details
    @Operation(summary = "Update an existing order detail", description = "Updates information for an existing order detail")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Order detail updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetails.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid order detail data",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Order detail not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })

    @PutMapping("/{orderId}/{ticketId}")
    public ResponseEntity<?> updateOrderDetail(
        @PathVariable Integer orderId,
        @PathVariable Integer ticketId,
        @Valid @RequestBody OrderDetails orderDetails) {
        
        OrderDetails.OrderDetailsId id = new OrderDetails.OrderDetailsId(orderId, ticketId);

         return orderDetailsRepository.findById(id)
        .map(d -> ResponseEntity.ok((Object) d))
        .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Order detail not found")));
    }

        // DELETE /api/orders/{id} - Delete order detail by ID
        
    @Operation(summary = "Delete order detail by ID", description = "Deletes a single order detail by its orderId and ticketId")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order detail deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted order detail with id {id}\"}"))),
        
@ApiResponse(
            responseCode = "404",
            description = "Order detail not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })  


    @DeleteMapping("/{orderId}/{ticketId}")
    public ResponseEntity<?> deleteOrderDetailById(
        @PathVariable Integer orderId,
        @PathVariable Integer ticketId) {
        
        OrderDetails.OrderDetailsId id = new OrderDetails.OrderDetailsId(orderId, ticketId);
          if (orderDetailsRepository.existsById(id)){
            orderDetailsRepository.deleteById(id);
             return ResponseEntity.ok(
                Map.of("message", "Successfully deleted order detail with id " + id) 
             );
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Order detail not found"));
        }
    }
}


