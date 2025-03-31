package com.fintrackerapi.fintracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/debug")
@RestController
public class DebugController {

    @GetMapping("/auth-info")
    public ResponseEntity<Map<String, Object>> debugAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("isAuthenticated", auth.isAuthenticated());
        debugInfo.put("principalType", auth.getPrincipal().getClass().getName());
        debugInfo.put("principalToString", auth.getPrincipal().toString());

        // Add available properties from principal
        if (auth.getPrincipal() instanceof com.fintrackerapi.fintracker.entities.User) {
            com.fintrackerapi.fintracker.entities.User user =
                    (com.fintrackerapi.fintracker.entities.User) auth.getPrincipal();

            Map<String, Object> userProps = new HashMap<>();
            userProps.put("id", user.getId());
            userProps.put("email", user.getEmail());
            userProps.put("fullName", user.getFullName());

            debugInfo.put("userProperties", userProps);
        }

        return ResponseEntity.ok(debugInfo);
    }

    @GetMapping("/simple-map")
    public ResponseEntity<Map<String, Object>> simpleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "This is a test");
        map.put("number", 123);

        return ResponseEntity.ok(map);
    }
}