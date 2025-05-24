package com.petexpress.israel.exceptions;

public record ErrorResponseDto(
        int status,
        String message,
        String timeStamp
) {}

