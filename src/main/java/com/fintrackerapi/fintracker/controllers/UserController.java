package com.fintrackerapi.fintracker.controllers;

import com.fintrackerapi.fintracker.dtos.UserDto;
import com.fintrackerapi.fintracker.entities.User;
import com.fintrackerapi.fintracker.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserDto userDto = new UserDto(
                currentUser.getId(),
                currentUser.getFullName(),
                currentUser.getEmail(),
                currentUser.getUsername(),
                currentUser.getCreatedAt()
        );
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserDto> userDtos = userService.allUsers();
        return ResponseEntity.ok(userDtos);
    }
}
