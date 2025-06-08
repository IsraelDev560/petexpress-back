package com.petexpress.israel.dto.res;

import com.petexpress.israel.entities.User;

import java.util.ArrayList;
import java.util.UUID;


public record UserResponseDto(UUID id, String username, User.Role role, boolean enabled) {
}
