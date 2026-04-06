package io.ggroup.demo.controller;

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
@RequestMapping("/api/inspect")
@Tag(name = "Inspect API", description = "Endpoints for managing ticket inspections")
public class InspectController {

    private final TicketTypeRepository ticketTypeRepository;
    private final IssuedTicketRepository issuedTicketRepository;

    public InspectController(IssuedTicketRepository issuedTicketRepository, TicketTypeRepository ticketTypeRepository) {
        this.issuedTicketRepository = issuedTicketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    // GET /api/inspect/{qrCode} - Get issued ticket by QR code
    @Operation (summary = "Get issued ticket by QR code", description = "Returns a single issued ticket by its QR code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Issued ticket found with QR code",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = IssuedTicket.class))),
        @ApiResponse(responseCode = "404", description = "Issued ticket not found with QR code",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{qrCode}")
    public ResponseEntity<?> inspectTicket(@PathVariable String qrCode) {
        return issuedTicketRepository.findByQrCode(qrCode)
                .map(issuedTicket -> {
                    if (issuedTicket.isUsed()) {
                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(new ErrorResponse(409, "Ticket has already been used"));
                    }

                    issuedTicket.setUsed(true);
                    issuedTicketRepository.save(issuedTicket);

                    return ResponseEntity.ok((Object) issuedTicket);
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Issued ticket not found with QR code")));
    }
}
