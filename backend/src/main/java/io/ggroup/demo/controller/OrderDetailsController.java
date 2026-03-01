package io.ggroup.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Post /api/orderdetails - Create a new order detail
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
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetails orderDetails) {
        try {
            OrderDetails savedDetail = orderDetailsRepository.save(orderDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDetail);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid order detail data: " + e.getMessage()));
        }
    }

}