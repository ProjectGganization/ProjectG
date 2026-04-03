package io.ggroup.demo.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.ggroup.demo.dto.*;
import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer API", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // POST /api/customers - Create a new customer
    @Operation(summary = "Create a new customer", description = "Adds a new customer to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Customer created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid customer data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest request) {
        // DTO validation
        try {
            Customer customer = new Customer();
            customer.setFirstname(request.getFirstname());
            customer.setLastname(request.getLastname());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());

            Customer savedCustomer = customerRepository.save(customer);

            // Map entity to response DTO
            CustomerResponse response = new CustomerResponse();
            response.setCustomerId(savedCustomer.getCustomerId());
            response.setFirstname(savedCustomer.getFirstname());
            response.setLastname(savedCustomer.getLastname());
            response.setEmail(savedCustomer.getEmail());
            response.setPhone(savedCustomer.getPhone());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Invalid customer data: " + e.getMessage()));
        }
    }

    // GET /api/customers - Get all customers
    @Operation(summary = "Get all customers", description = "Returns a list of all customers")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "All customers found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No customers found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "No customers found"));
        } else {
            return ResponseEntity.ok(customers);
        }
    }

    // GET /api/customers/{id} - Get customer by ID
    @Operation(summary = "Get customer by ID", description = "Returns a single customer by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        try {
            var opt = customerRepository.findById(id);

            if (opt.isPresent()) {
                Customer customer = opt.get();
                
                // Map entity to response DTO
                CustomerResponse response = new CustomerResponse();
                response.setCustomerId(customer.getCustomerId());
                response.setFirstname(customer.getFirstname());
                response.setLastname(customer.getLastname());
                response.setEmail(customer.getEmail());
                response.setPhone(customer.getPhone());

                return ResponseEntity.ok(response);
            }

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Customer not found with ID " + id));

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "An error occurred while retrieving the customer: " + e.getMessage()));

            
        }
    }

    // UPDATE /api/customers/{id} - Update an existing customer
    @Operation(summary = "Update an existing customer", description = "Updates information for an existing customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "400", description = "Invalid customer data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Customer not found with ID: " + id));
        }
  
        existingCustomer.setFirstname(customer.getFirstname());
        existingCustomer.setLastname(customer.getLastname());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        existingCustomer.setUser(customer.getUser());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // DELETE /api/customers/{id} - Delete customer by ID
    @Operation(summary = "Delete customer by ID", description = "Deletes a single customer by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer deleted succesfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted customer with id {id}\"}"))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.ok().body(Map.of("message", "Successfully deleted customer with id " + id));
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Customer not found"));
        }
    }

}
