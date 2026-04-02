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
@RequestMapping("/api/events")
@Tag(name = "Event API", description = "Endpoints for managing events")
public class EventsController {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventsController(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    // Esimerkki toimivasta endpointista. Kun olet tehnyt oman endpointin valmiiksi niin tämä endpoint näyttää uusien endpointtien määrän.
    @Operation(summary = "Get event count", description = "Returns the total number of events")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved event count")
    @GetMapping("/count")
    public ResponseEntity<Long> getEventCount() {
        long count = eventRepository.count();
        return ResponseEntity.ok(count);
    }

    // GET /api/events - Get all events
    @Operation(summary = "Get all events", description = "Returns a list of all events")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "All events found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No events found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "No events found"));
        } else {
            return ResponseEntity.ok(events);
        }
    }

    // GET /api/events/{id} - Search event by ID
    @Operation(summary = "Get event by ID", description = "Returns a single event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .map(event -> ResponseEntity.ok((Object) event))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Event not found")));
    }

    // POST /api/events - Create a new event
    @Operation(summary = "Create a new event", description = "Adds a new event to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Invalid event data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        ResponseEntity<?> venueValidation = validateAndAttachVenue(event);
        if (venueValidation != null) {
            return venueValidation;
        }

        try {
            Event savedEvent = eventRepository.save(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Invalid event data: " + e.getMessage()));
        }
    }

    // PUT /api/events/{id} - Update an existing event
    @Operation(summary = "Update an existing event", description = "Updates the details of an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Invalid event data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        Event existingEvent = eventRepository.findById(id).orElse(null);
        if (existingEvent == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Event not found with ID: " + id));
        }

        ResponseEntity<?> venueValidation = validateAndAttachVenue(event);
        if (venueValidation != null) {
            return venueValidation;
        }

        if (event.getEventStatus() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Event status is required"));
        }

        if (event.getCategory() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Category is required"));
        }

        if (event.getEndTime() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "End time is required"));
        }

        if (event.getStartTime() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Start time is required"));
        }

        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setPhoto(event.getPhoto());
        existingEvent.setStartTime(event.getStartTime());
        existingEvent.setEndTime(event.getEndTime());
        existingEvent.setEventStatus(event.getEventStatus());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setCategory(event.getCategory());

        Event updatedEvent = eventRepository.save(existingEvent);
        return ResponseEntity.ok(updatedEvent);
    }

    // DELETE /api/events/{id} - Delete event by ID
   @Operation(
    summary = "Delete event by ID",
    description = "Deletes a single event by its ID and returns a message containing the deleted ID"
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Event deleted successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                type = "object",
                example = "{\"message\": \"Successfully deleted event with id {id}\"}"
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Event not found",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
})

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Integer id) {
         if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);

            return ResponseEntity.ok(
                Map.of("message", "Successfully deleted event with id " + id)
            );
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Event not found"));
        }
    }


        private ResponseEntity<?> validateAndAttachVenue(Event event) {
        if (event.getVenue() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Venue is required"));
        }

        Integer venueId = event.getVenue().getVenueId();
        if (venueId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Venue id is required"));
        }

        Venue existingVenue = venueRepository.findById(venueId).orElse(null);
        if (existingVenue == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Venue not found"));
        }

        event.setVenue(existingVenue);
        return null; // kaikki ok
    }
}

