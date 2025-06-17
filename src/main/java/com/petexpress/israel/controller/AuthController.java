package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.AuthenticationRequestDto;
import com.petexpress.israel.dto.request.RegisterRequestDto;
import com.petexpress.israel.dto.res.AuthResponseDto;
import com.petexpress.israel.dto.res.RegisterResponseDto;
import com.petexpress.israel.entities.User;
import com.petexpress.israel.exceptions.ErrorResponseDto;
import com.petexpress.israel.exceptions.GlobalExceptionHandler;
import com.petexpress.israel.exceptions.UserExceptions;
import com.petexpress.israel.repository.UserRepository;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.TokenService;
import com.petexpress.israel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(
        name = "Auth",
        description = "Endpoints for user authentication, login and registration."
)
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Login", description = "This route authenticates the user.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns an authentication token.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthenticationRequestDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @Operation(summary = "Register a new user", description = "This route registers a new user.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns a JSON response with the newly created user's information."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid token or user does not have permission to create another user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto data) {
        try {
            if (this.repository.findByUsername(data.username()) != null)
                return ResponseEntity.status(HttpStatus.CONFLICT).build();

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.username(), encryptedPassword, data.role());
            this.userService.createUser(newUser);

            return ResponseEntity.ok(new RegisterResponseDto(newUser.getUsername(), newUser.getRole()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
