package org.example.psychologicalcounseling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupervisorArrangementController {
    @GetMapping("/api/getSupervisorOrder")
    public ResponseEntity<?> getSupervisorOrder(int supervisorID) {
        return null;
    }

    @PostMapping("/api/addSupervisorOrder")
    public ResponseEntity<?> addSupervisorOrder(int supervisorID) {
        return null;
    }

    @PostMapping("/api/cancelSupervisorOrder")
    public ResponseEntity<?> cancelSupervisorOrder(int supervisorID) {
        return null;
    }
}
