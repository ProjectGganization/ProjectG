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
import io.ggroup.demo.model.Event;
import io.ggroup.demo.model.Ticket;
import io.ggroup.demo.repository.EventRepository;
import io.ggroup.demo.repository.TicketRepository;
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
        if (!ticketRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Ticket not found"));
        }

        ResponseEntity<?> eventValidation = validateAndAttachEvent(ticket);
        if (eventValidation != null) {
            return eventValidation;
        }
        try {
            ticket.setTicketId(id);
            Ticket updatedTicket = ticketRepository.save(ticket);
            return ResponseEntity.ok(updatedTicket);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid ticket data: " + e.getMessage()));
        }
    }

    // DELETE /api/tickets/{id} - Delete ticket by ID
    @Operation(summary = "Delete ticket by ID", description = "Deletes a single ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable Integer id) {
        if (ticketRepository.existsById(id)) {
            try {
                ticketRepository.deleteById(id);
                return ResponseEntity.noContent().build();
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