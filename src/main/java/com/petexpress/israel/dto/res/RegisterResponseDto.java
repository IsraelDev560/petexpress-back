package com.petexpress.israel.dto.res;

import com.petexpress.israel.entities.User;

public record RegisterResponseDto(String username, User.Role role) {
}
