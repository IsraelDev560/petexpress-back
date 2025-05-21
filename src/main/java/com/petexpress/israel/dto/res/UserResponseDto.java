package com.petexpress.israel.dto.res;

import com.petexpress.israel.entities.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String username;
    private String email;
    private String role;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = String.valueOf(user.getRole());
    }
}
