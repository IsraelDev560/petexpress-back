package com.petexpress.israel.dto.res;

import com.petexpress.israel.entities.User;

public record UserInfoDto(String username, User.Role role) {
}
