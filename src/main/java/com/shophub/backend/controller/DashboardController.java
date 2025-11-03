package com.shophub.backend.controller;

import com.shophub.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/orders/recent")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getRecentOrders() {
        return ResponseEntity.ok(dashboardService.getRecentOrders());
    }

    @GetMapping("/products/top")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getTopProducts() {
        return ResponseEntity.ok(dashboardService.getTopProducts());
    }
}
