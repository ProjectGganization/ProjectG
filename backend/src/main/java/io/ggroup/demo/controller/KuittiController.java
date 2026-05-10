package io.ggroup.demo.controller;

import io.ggroup.demo.dto.KuittiDTO;
import io.ggroup.demo.service.KuittiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kuitti")
@Tag(name = "Kuitti API", description = "Endpoint for fetching receipt by order id")
public class KuittiController {

    private final KuittiService kuittiService;

    public KuittiController(KuittiService kuittiService) {
        this.kuittiService = kuittiService;
    }

    @Operation(summary = "Get a receipt", description = "Returns a receipt by its order id")
    @GetMapping("/{orderId}")
    public List<KuittiDTO> haeKuitti(@PathVariable Integer orderId) {
        return kuittiService.haeKuitti(orderId);
    }

    @Operation(summary = "Send a receipt", description = "Send a receipt to a recipent with order id")
    @PostMapping("/{orderId}/email")
    public ResponseEntity<Void> lahetaKuitti(
            @PathVariable Integer orderId,
            @RequestParam String email
    ) {
        kuittiService.lahetaKuitti(orderId, email);
        return ResponseEntity.noContent().build();
    }
    
}