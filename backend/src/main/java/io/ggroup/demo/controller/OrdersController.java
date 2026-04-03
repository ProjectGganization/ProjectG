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
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Endpoints for managing orders")
public class OrdersController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrdersController(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
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
        // Validation
        ResponseEntity<?> customerValidation = validateAndAttachCustomer(order);
        if (customerValidation != null) return customerValidation;
    
        try {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Invalid order data: " + e.getMessage()));
        }
    }

    // UPDATE /api/orders/{id} - Update an existing order
    @Operation(summary = "Update an existing order", description = "Updates information for an existing order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid order data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Order not found"));
        }

        ResponseEntity<?> customerValidation = validateAndAttachCustomer(order);
        if (customerValidation != null) {
            return customerValidation;
        }

        if (order.getCustomer() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Customer is required"));
        }

        if (order.getDate() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Date is required"));
        }

        if (order.getSeller() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Seller is required"));
        }

        existingOrder.setCustomer(order.getCustomer());  // tämä on validateAndAttachCustomer() kautta DB-entity
        existingOrder.setDate(order.getDate());
        existingOrder.setSeller(order.getSeller());
        existingOrder.setIsRefunded(order.getIsRefunded());
        existingOrder.setIsPaid(order.getIsPaid());
        existingOrder.setPaymentMethod(order.getPaymentMethod());

        Order updatedOrder = orderRepository.save(existingOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    // DELETE /api/orders/{id} - Delete order by ID
    @Operation(summary = "Delete order by ID", description = "Deletes a single order by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted order with id {id}\"}"))),
        @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok(
                Map.of("message", "Successfully deleted order with id " + id) 
             );
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Order not found"));
        }
    }

    // CustomerID validation
    private ResponseEntity<?> validateAndAttachCustomer(Order order) {
        if (order.getCustomer() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Customer is required"));
        }

        Integer customerId = order.getCustomer().getCustomerId();
        if (customerId == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Customer id is required"));
        }

        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Customer with id " + customerId + " does not exist"));
        }

        order.setCustomer(existingCustomer);
        return null;
    }
}
