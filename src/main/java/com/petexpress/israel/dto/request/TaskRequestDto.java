package com.petexpress.israel.dto.request;


import java.time.LocalDate;
import java.util.UUID;

public record TaskRequestDto(
        UUID taskTypeId,
        UUID animalId,
        LocalDate date
) {}
