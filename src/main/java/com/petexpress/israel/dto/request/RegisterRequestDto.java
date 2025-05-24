package com.petexpress.israel.dto.request;

import com.petexpress.israel.entities.User;

public record RegisterRequestDto(String username, String password, User.Role role) {
}
