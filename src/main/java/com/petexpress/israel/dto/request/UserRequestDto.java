package com.petexpress.israel.dto.request;


import com.petexpress.israel.entities.User;

public record UserRequestDto(String username, User.Role role) {
}
