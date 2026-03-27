package io.ggroup.demo.controller;

import java.util.List;
import java.util.Map;

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
import io.ggroup.demo.model.PostalCode;
import io.ggroup.demo.model.Venue;
import io.ggroup.demo.repository.PostalCodeRepository;
import io.ggroup.demo.repository.VenueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/venues")
@Tag(name = "Venue API", description = "Endpoints for managing venues")
public class VenuesController {

	private final VenueRepository venueRepository;
	private final PostalCodeRepository postalCodeRepository;

	public VenuesController(VenueRepository venueRepository, PostalCodeRepository postalCodeRepository) {
		this.venueRepository = venueRepository;
		this.postalCodeRepository = postalCodeRepository;
	}

	@Operation(summary = "Get all venues", description = "Returns a list of all venues")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "All venues found successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venue.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "No venues found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
	@GetMapping
	public ResponseEntity<?> getAllVenues() {
		List<Venue> venues = venueRepository.findAll();
		if (venues.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(404, "No venues found"));
		}
		return ResponseEntity.ok(venues);
	}

	@Operation(summary = "Get venue by ID", description = "Returns a single venue by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Venue found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venue.class))),
			@ApiResponse(responseCode = "404", description = "Venue not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getVenueById(@PathVariable Integer id) {
		return venueRepository.findById(id)
				.map(venue -> ResponseEntity.ok((Object) venue))
				.orElseGet(() -> ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse(404, "Venue not found")));
	}

	@Operation(summary = "Create a new venue", description = "Adds a new venue to the system")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Venue created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venue.class))),
			@ApiResponse(responseCode = "400", description = "Invalid venue data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping
	public ResponseEntity<?> createVenue(@RequestBody Venue venue) {
		ResponseEntity<?> postalCodeValidation = validateAndAttachPostalCode(venue);
		if (postalCodeValidation != null) {
			return postalCodeValidation;
		}
		try {
			Venue savedVenue = venueRepository.save(venue);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(400, "Invalid venue data: " + e.getMessage()));
		}
	}

	@Operation(summary = "Update an existing venue", description = "Updates the details of an existing venue")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Venue updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venue.class))),
			@ApiResponse(responseCode = "400", description = "Invalid venue data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Venue not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVenue(@PathVariable Integer id, @RequestBody Venue venue) {
		if (!venueRepository.existsById(id)) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(404, "Venue not found"));
		}

		ResponseEntity<?> postalCodeValidation = validateAndAttachPostalCode(venue);
		if (postalCodeValidation != null) {
			return postalCodeValidation;
		}

		try {
			venue.setVenueId(id);
			Venue updatedVenue = venueRepository.save(venue);
			return ResponseEntity.ok(updatedVenue);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(400, "Invalid venue data: " + e.getMessage()));
		}
	}

	@Operation(summary = "Delete venue by ID", description = "Deletes a single venue by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Venue deleted successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"message\": \"Successfully deleted venue with id {id}\"}"))),
			@ApiResponse(responseCode = "404", description = "Venue not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVenueById(@PathVariable Integer id) {
		if (venueRepository.existsById(id)) {
			venueRepository.deleteById(id);
			return ResponseEntity.ok(Map.of("message", "Successfully deleted venue with id " + id));
		}
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(404, "Venue not found"));
	}

	private ResponseEntity<?> validateAndAttachPostalCode(Venue venue) {
		if (venue.getPostalCode() == null) {
			return null;
		}

		String postalCodeValue = venue.getPostalCode().getPostalCode();
		if (postalCodeValue == null || postalCodeValue.trim().isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(400, "Postal code is required when postalCode object is provided"));
		}

		PostalCode existingPostalCode = postalCodeRepository.findById(postalCodeValue)
				.orElse(null);
		if (existingPostalCode == null) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(404, "Postal code not found"));
		}

		venue.setPostalCode(existingPostalCode);
		return null;
	}
}
