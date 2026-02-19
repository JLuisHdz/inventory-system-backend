package com.inventory.inventorysystem.mapper;

import com.inventory.inventorysystem.dto.user.UserResponse;
import com.inventory.inventorysystem.entity.Role;
import com.inventory.inventorysystem.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.isActive(),
                user.getCreationDate(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }

}
