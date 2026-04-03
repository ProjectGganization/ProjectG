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
@RequestMapping("/api/tickets")
@Tag(name = "Ticket API", description = "Endpoints for managing tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    // GET /api/tickets/{id} - Get all tickets
    @Operation(summary = "Get all tickets", description = "Returns a list of all available tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "No tickets found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "No tickets found"));
        } else {
            return ResponseEntity.ok(tickets);
        }
    }

    // GET /api/tickets/{id} - Get ticket by ID
    @Operation(summary = "Get ticket by ID", description = "Returns a single ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Integer id) {
        return ticketRepository.findById(id)
                .map(ticket -> ResponseEntity.ok((Object) ticket))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Ticket not found")));
    }

    // POST /api/tickets - Create a new ticket type
    @Operation(summary = "Create a new ticket", description = "Creates a new ticket type for an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
        ResponseEntity<?> eventValidation = validateAndAttachEvent(ticket);
        if (eventValidation != null) {
            return eventValidation;
        }
        try {
            Ticket savedTicket = ticketRepository.save(ticket);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid ticket data: " + e.getMessage()));
        }
    }

    // PUT /api/tickets/{id} - Update an existing ticket
    @Operation(summary = "Update an existing ticket", description = "Updates information for an existing ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ticket data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Integer id, @RequestBody Ticket ticket) {
        Ticket existingTicket = ticketRepository.findById(id).orElse(null);
        if (existingTicket == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Ticket not found"));
        }

        ResponseEntity<?> eventValidation = validateAndAttachEvent(ticket);
        if (eventValidation != null) {
            return eventValidation;
        }

        if (ticket.getTicketType() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Ticket type is required"));
        }

        if (ticket.getEvent() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Event is required"));
        }

        if (ticket.getUnitPrice() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Unit price is required"));
        }

        if (ticket.getInStock() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "In stock quantity is required"));
        }

        existingTicket.setTicketType(ticket.getTicketType());
        existingTicket.setEvent(ticket.getEvent());
        existingTicket.setUnitPrice(ticket.getUnitPrice());
        existingTicket.setInStock(ticket.getInStock());

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return ResponseEntity.ok(updatedTicket);
    }

    // DELETE /api/tickets/{id} - Delete ticket by ID
    @Operation(summary = "Delete ticket by ID", description = "Deletes a single ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted ticket with id {id}\"}"))),

            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable Integer id) {
        if (ticketRepository.existsById(id)) {
            try {
                ticketRepository.deleteById(id);
                return ResponseEntity.ok(Map.of ("message", "Successfully deleted ticket with id " + id));
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Cannot delete ticket: it may be linked to existing orders."));
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Ticket not found"));
        }
    }

    // Validation
    private ResponseEntity<?> validateAndAttachEvent(Ticket ticket) {
        if (ticket.getEvent() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Event is required"));
        }

        Integer eventId = ticket.getEvent().getEventId();
        if (eventId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Event id is required"));
        }

        Event exisitingEvent = eventRepository.findById(eventId).orElse(null);
        if (exisitingEvent == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Event with id " + eventId + " does not exist"));
        }

        ticket.setEvent(exisitingEvent);
        return null;
    }

}