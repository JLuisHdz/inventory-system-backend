package com.inventory.inventorysystem.controller;

import com.inventory.inventorysystem.dto.common.ApiResponse;
import com.inventory.inventorysystem.dto.user.UserRequest;
import com.inventory.inventorysystem.dto.user.UserResponse;
import com.inventory.inventorysystem.entity.User;
import com.inventory.inventorysystem.mapper.UserMapper;
import com.inventory.inventorysystem.repository.UserRepository;
import com.inventory.inventorysystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final UserRepository userRepository;

    public UserController(UserService userService, UserMapper userMapper,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request){
        User createdUser = userService.createUser(request);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User created successfully",
                        userMapper.toResponse(createdUser)
                )
        );
    }

    @Operation(
            summary = "Get all users",
            description = "Returns paginated list of all registered users"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public com.inventory.inventorysystem.dto.common.ApiResponse<Page<UserResponse>> getUsers(
            @ParameterObject Pageable pageable
    ) {

        Page<UserResponse> users = userRepository.findAll(pageable)
                .map(userService::toResponse);

        return new com.inventory.inventorysystem.dto.common.ApiResponse<>(
                true,
                "Users retrieved successfully",
                users
        );
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userService.toResponse(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }
}
