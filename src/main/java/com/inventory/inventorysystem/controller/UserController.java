package com.inventory.inventorysystem.controller;

import com.inventory.inventorysystem.dto.common.ApiResoponse;
import com.inventory.inventorysystem.dto.user.UserRequest;
import com.inventory.inventorysystem.dto.user.UserResponse;
import com.inventory.inventorysystem.entity.User;
import com.inventory.inventorysystem.mapper.UserMapper;
import com.inventory.inventorysystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(
        name = "Users",
        description = "User management APIs"
)
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ApiResoponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request){
        User createdUser = userService.createUser(request);
        return ResponseEntity.ok(
                new ApiResoponse<>(
                        true,
                        "User created successfully",
                        userMapper.toResponse(createdUser)
                )
        );
    }

    @Operation(
            summary = "Get all users",
            description = "Returns list of all registered users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}
