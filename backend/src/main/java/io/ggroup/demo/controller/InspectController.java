package io.ggroup.demo.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.ggroup.demo.dto.InspectResponseDTO;
import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inspect")
@Tag(name = "Inspect API", description = "Endpoints for managing ticket inspections")
public class InspectController {

    private final IssuedTicketRepository issuedTicketRepository;

    public InspectController(IssuedTicketRepository issuedTicketRepository) {
        this.issuedTicketRepository = issuedTicketRepository;
    }

    @Operation(summary = "Get ticket by QR code", description = "Returns full ticket info by QR code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket found"),
        @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/{qrCode}")
    @Transactional
    public ResponseEntity<?> getTicketByQrCode(@PathVariable String qrCode) {
        return issuedTicketRepository.findByQrCode(qrCode)
                .map(ticket -> ResponseEntity.ok((Object) new InspectResponseDTO(ticket)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Issued ticket not found with QR code")));
    }

    @Operation(summary = "Mark ticket as used", description = "Marks a ticket as used by its QR code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket marked as used"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "409", description = "Ticket has already been used")
    })
    @PutMapping("/{qrCode}/use")
    @Transactional
    public ResponseEntity<?> markTicketUsed(@PathVariable String qrCode) {
        return issuedTicketRepository.findByQrCode(qrCode)
                .map(issuedTicket -> {
                    if (issuedTicket.isUsed()) {
                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(new ErrorResponse(409, "Ticket has already been used"));
                    }
                    issuedTicket.setUsed(true);
                    issuedTicketRepository.save(issuedTicket);

                    return ResponseEntity.ok((Object) new InspectResponseDTO(issuedTicket));
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Issued ticket not found with QR code")));
    }
}
