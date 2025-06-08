package com.petexpress.israel.dto.res;

import com.petexpress.israel.entities.User;

import java.util.ArrayList;
import java.util.UUID;

public record UserUpdateResponseDto (UUID id, String username, User.Role role, ArrayList<? extends org.springframework.security.core.GrantedAuthority> authorities){
}
