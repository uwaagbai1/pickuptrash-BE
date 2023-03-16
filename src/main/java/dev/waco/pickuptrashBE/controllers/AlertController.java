package dev.waco.pickuptrashBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

import dev.waco.pickuptrashBE.services.AlertService;
import dev.waco.pickuptrashBE.models.Alert;
import dev.waco.pickuptrashBE.security.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    @Autowired
    private AlertService alertService;

    @PostMapping("/create")
    public ResponseEntity<Alert> createAlert(@Valid @RequestBody Alert alert, @CurrentUser UserPrincipal userPrincipal) {
        Alert createdAlert = alertService.createAlert(alert, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
    }

    @GetMapping("")
    public ResponseEntity<List<Alert>> getAllAlerts() {
        List<Alert> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Alert>> getAllAlertsByUserId(@PathVariable Long userId, @CurrentUser UserPrincipal userPrincipal) {
        if (!userPrincipal.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Alert> alerts = alertService.getAllAlertsByUserId(userId);
        return ResponseEntity.ok(alerts);
    }
}

