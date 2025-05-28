package com.petexpress.israel.dto.res;

import java.util.UUID;

public record TaskTypeResponseDto(
        UUID id,
        String name,
        String description
) {}
