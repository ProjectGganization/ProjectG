package io.ggroup.demo.controller;

import io.ggroup.demo.model.ErrorResponse;
import io.ggroup.demo.model.Order;
import io.ggroup.demo.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Endpoints for managing orders")
public class OrdersController {

    private final OrderRepository orderRepository;

    public OrdersController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // GET /api/orders - Get all orders
    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "All orders found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No orders found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "No orders found"));
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    // GET /api/orders/{id} - Get order by ID
    @Operation(summary = "Get order by ID", description = "Returns a single order by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id) {
        return orderRepository.findById(id)
        .map(order -> ResponseEntity.ok((Object) order))
        .orElseGet(() -> ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(404, "Order not found")));
}

// POST /api/orders - Create a new order
    @Operation(summary = "Create a new order", description = "Adds a new order to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid order data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Invalid order data: " + e.getMessage()));
        }
    }
}
