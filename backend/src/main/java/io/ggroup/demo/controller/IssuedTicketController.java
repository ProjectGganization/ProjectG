package io.ggroup.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

import io.ggroup.demo.model.IssuedTicket;
import io.ggroup.demo.model.ErrorResponse;
import io.ggroup.demo.repository.IssuedTicketRepository;
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

    public IssuedTicketController(IssuedTicketRepository issuedTicketRepository){
        this.issuedTicketRepository = issuedTicketRepository;
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
}

