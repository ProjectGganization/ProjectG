package io.ggroup.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Hello API", description = "Basic API endpoints for testing connectivity")
public class HelloController {

    @Operation(summary = "Get greeting message", description = "Returns a simple greeting message from the backend")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting message")
    })
    @GetMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Spring Boot!");
        response.put("status", "success");
        return response;
    }

    @Operation(summary = "Get server status", description = "Returns current server status and timestamp")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved server status")
    })
    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "online");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }


    @Operation(summary = "Update greeting message", description = "Updates the greeting message")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Message updated successfully")
})
@PutMapping("/hello")
public Map<String, String> updateHello(@RequestBody Map<String, String> request) {
    Map<String, String> response = new HashMap<>();

    String newMessage = request.get("message");

    response.put("message", newMessage);
    response.put("status", "updated");

    return response;
}
}

