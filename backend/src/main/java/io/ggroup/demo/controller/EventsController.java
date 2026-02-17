package io.ggroup.demo.controller;

import io.ggroup.demo.exception.EventNotFoundException;
import io.ggroup.demo.model.ErrorResponse;
import io.ggroup.demo.model.Event;
import io.ggroup.demo.repository.EventRepository;
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
@RequestMapping("/api/events")
@Tag(name = "Event API", description = "Endpoints for managing events")
public class EventsController {

    private final EventRepository eventRepository;

    public EventsController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

 //Esimerkki toimivasta endpointista. Kun olet tehnyt oman endpointin valmiiksi niin tämä endpoint näyttää uusien endpointtien määrän.
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
        @ApiResponse(
            responseCode = "200", 
            description = "Event found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Event not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .map(event -> ResponseEntity.ok((Object) event))
                .orElseGet(() -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "Event not found")));
    }

    // DELETE /api/events/{id} - Delete event by ID
    @Operation(summary = "Delete event by ID", description = "Deletes a single event by its ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Event deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Event not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Integer id) {
        if(eventRepository.existsById(id)){
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Event not found"));
        }
    }

    // ==================== TODO: Nämä tulisi luoda ====================
    //
    // 1. GET    /api/events       - Get all events (return List<Event>)
    // 2. GET    /api/events/{id}  - Get a single event by ID (return 404 if not found)
    // 3. POST   /api/events       - Create a new event (return 201 status)
    // 4. PUT    /api/events/{id}  - Update an existing event (return 404 if not found)
    // 5. DELETE /api/events/{id}  - Delete an event (return 204 on success, 404 if not found)


}
