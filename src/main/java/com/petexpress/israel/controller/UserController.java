package com.petexpress.israel.controller;

import com.petexpress.israel.dto.res.UserUpdateResponseDto;
import com.petexpress.israel.dto.update.UserUpdateDto;
import com.petexpress.israel.dto.res.UserResponseDto;
import com.petexpress.israel.entities.User;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@Tag(name = "Users", description = "Manage user accounts and permissions")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        var users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getRole(), user.isEnabled()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Fetch a specific user by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername(), user.getRole(), user.isEnabled()));
    }

    @Operation(summary = "Get information about the authenticated user", description = "Returns details about the currently authenticated user.")
    @GetMapping("/myinfo")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails){
        UserResponseDto user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok( new UserResponseDto(user.id(), user.username(), user.role(), user.enabled()));

    }

    @Operation(summary = "Update user by ID", description = "Partially updates a user's information using their ID.")
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable UUID id, @RequestBody UserUpdateDto dto) {
        User updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(new UserUpdateResponseDto(updated.getId(), updated.getUsername(), updated.getRole(), (ArrayList<? extends GrantedAuthority>) updated.getAuthorities()));
    }

    @Operation(summary = "Delete user by ID", description = "Deletes a user account based on their ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
