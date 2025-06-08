package com.petexpress.israel.dto.request;


import java.time.LocalDate;
import java.util.UUID;

public record TaskRequestDto(
        String taskTypeName,
        UUID animalId,
        String description,
        LocalDate date
) {}
