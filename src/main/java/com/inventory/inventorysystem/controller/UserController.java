package com.inventory.inventorysystem.controller;

import com.inventory.inventorysystem.dto.auth.user.UserResponse;
import com.inventory.inventorysystem.entity.User;
import com.inventory.inventorysystem.mapper.UserMapper;
import com.inventory.inventorysystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.toResponse(createdUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}
