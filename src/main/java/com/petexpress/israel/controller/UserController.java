package com.petexpress.israel.controller;

import com.petexpress.israel.dto.res.UserUpdateResponseDto;
import com.petexpress.israel.dto.update.UserUpdateDto;
import com.petexpress.israel.dto.res.UserResponseDto;
import com.petexpress.israel.entities.User;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("users")
@Tag(name = "Users")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        var users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getRole(), user.getAuthorities(), user.isEnabled()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stream<UserResponseDto>> getUserById(@Valid UUID id) {
        Stream<UserResponseDto> user = userService.getUserById(id).stream().map(u -> new UserResponseDto(u.getId(), u.getUsername(), u.getRole(), u.getAuthorities(), u.isEnabled()));
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable UUID id, @RequestBody UserUpdateDto dto) {
        User updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(new UserUpdateResponseDto(updated.getId(), updated.getUsername(), updated.getRole(), updated.getAuthorities()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
