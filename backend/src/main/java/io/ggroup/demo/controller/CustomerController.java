package io.ggroup.demo.controller;

import io.ggroup.demo.dto.CreateCustomerRequest;
import io.ggroup.demo.dto.CustomerResponse;
import io.ggroup.demo.model.Customer;
import io.ggroup.demo.model.ErrorResponse;
import io.ggroup.demo.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .body(new ErrorResponse(404, "Customer not found with ID" + id));

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "An error occurred while retrieving the customer: " + e.getMessage()));

            
        }
    }
}
