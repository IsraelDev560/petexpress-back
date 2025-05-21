package com.petexpress.israel.dto.request;

import com.petexpress.israel.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "O nome não pode ficar em branco")
    private String username;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email não pode ficar em branco")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private User.Role role;

    public enum Role {
        ADMIN, USER
    }
}
