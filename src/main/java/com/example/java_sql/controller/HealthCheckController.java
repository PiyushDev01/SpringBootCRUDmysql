package com.example.java_sql.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String healthCheck() {
        return "Application is running!";
    }
}
