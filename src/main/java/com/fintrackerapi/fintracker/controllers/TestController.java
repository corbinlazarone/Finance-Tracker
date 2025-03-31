package com.fintrackerapi.fintracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/one")
    public ResponseEntity<String> Test() {
        return ResponseEntity.ok("Success");
    }
}
