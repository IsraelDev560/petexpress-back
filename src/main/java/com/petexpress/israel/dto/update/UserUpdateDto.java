package com.petexpress.israel.dto.update;

import com.petexpress.israel.entities.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;


public record UserUpdateDto(String username, User.Role role, ArrayList<String> authorities) {
}
