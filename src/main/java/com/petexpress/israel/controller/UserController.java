package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.UserRequestDto;
import com.petexpress.israel.dto.update.UserUpdateDto;
import com.petexpress.israel.dto.res.UserResponseDto;
import com.petexpress.israel.entities.User;
import com.petexpress.israel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers() {
        List<UserResponseDto> userResponseDtos = this.userService.getAllUsers().stream().map(UserResponseDto::new).toList();
        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createNewUser(@Valid @RequestBody UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        User saved = userService.createUser(user);
        UserResponseDto responseDto = UserResponseDto.builder()
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role(String.valueOf(saved.getRole()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody UserUpdateDto dto) {
        User updated = userService.updateUser(id, dto);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
