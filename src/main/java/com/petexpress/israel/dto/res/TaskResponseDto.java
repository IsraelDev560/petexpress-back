package com.petexpress.israel.dto.res;

import java.time.LocalDate;
import java.util.UUID;

public record TaskResponseDto(
        UUID id,
        String taskName,
        String taskDescription,
        LocalDate date,
        UUID animalId,
        String animalName,
        String animalSpecie
) {}
