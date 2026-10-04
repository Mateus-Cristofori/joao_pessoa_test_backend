package com.example.demo.features.dashboard.controller;

import com.example.demo.features.dashboard.model.response.DashboardSummaryResponse;
import com.example.demo.features.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@AllArgsConstructor
public class DashboardController {


    private final DashboardService dashboardService;

    @GetMapping()
    public DashboardSummaryResponse dashboard() {
        return dashboardService.getDashboardSummary();
    }
}
