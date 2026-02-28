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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orderdetails")
@Tag(name = "OrderDetails API", description = "Endpoints for managing order details")
public class OrderDetailsController {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsController(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    // GET ALL
    @Operation(summary = "Get all order details")
    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails() {
    return ResponseEntity.ok(orderDetailsRepository.findAll());
}
    
    // GET BY ID
    @Operation(summary = "Get order details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailsById(@PathVariable Integer id) {
      if (orderDetailsRepository.isEmpty()) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Order details not found"));
      }
      else {
        return ResponseEntity.ok(orderDetailsRepository);
      }
        
}

    // CREATE
    @Operation(summary = "Create new order details")
    @PostMapping
    public ResponseEntity<?> createOrderDetails(@RequestBody OrderDetails orderDetails) {
        try {
            OrderDetails saved = orderDetailsRepository.save(orderDetails);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(saved);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid data: " + e.getMessage()));
        }
    }

    // UPDATE
    @Operation(summary = "Update order details")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetails(@PathVariable Integer id,
                                                @RequestBody OrderDetails orderDetails) {

        if (!orderDetailsRepository.exists(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Order details not found"));
        }

        try {
            orderDetails.setOrderId(id); // muuta tähän oikea ID-field nimi
            OrderDetails updated = orderDetailsRepository.save(orderDetails);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid data: " + e.getMessage()));
        }
    }

    // DELETE
    @Operation(summary = "Delete order details")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetailsById(@PathVariable Integer id) {

        if (orderDetailsRepository.exists(id)) {
            orderDetailsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Order details not found"));
    }
}