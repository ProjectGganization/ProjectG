package io.ggroup.demo.controller;


import java.util.List;

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
import io.ggroup.demo.model.OrderDetails;
import io.ggroup.demo.repository.OrderDetailsRepository;
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
    
     public OrderDetailsController(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
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
            .<ResponseEntity<?>>map(ResponseEntity::ok)
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
        ResponseEntity<?> validationResponse = validateAndAttachResponse(orderDetails);
        if (validationResponse != null) {
            return validationResponse;
        }
        try {
            OrderDetails savedDetail = orderDetailsRepository.save(orderDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDetail);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid order detail data: " + e.getMessage()));
        }
    }

    private ResponseEntity<?> validateAndAttachResponse(OrderDetails orderDetails) {
        
        throw new UnsupportedOperationException("Unimplemented method 'validateAndAttachResponse'");
    }

    // UPDATE /api/orders/{id} - Update an existing order detail
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
        @PathVariable Integer orderId,
        @PathVariable Integer ticketId,
        @Valid @RequestBody OrderDetails orderDetails) {
        
        OrderDetails.OrderDetailsId id = new OrderDetails.OrderDetailsId(orderId, ticketId);
        if (!orderDetailsRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Order detail not found"));
        }

        ResponseEntity<?> validationResponse = validateAndAttachResponse(orderDetails);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            orderDetails.setId(id);
            OrderDetails updatedDetail = orderDetailsRepository.save(orderDetails);
            return ResponseEntity.ok(updatedDetail);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid order detail data: " + e.getMessage()));
        }
    }
        // DELETE /api/orders/{id} - Delete order detail by ID
        
    @Operation(summary = "Delete order detail by ID", description = "Deletes a single order detail by its orderId and ticketId")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Order detail deleted successfully"
        ),
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
        if (orderDetailsRepository.existsById(id)) {
            orderDetailsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Order detail not found"));
        }
    }

   
}