package com.petexpress.israel.dto.update;

import com.petexpress.israel.entities.Animal;

import java.time.LocalDate;
import java.util.UUID;

public record TaskUpdateDto(
        UUID taskTypeId,
        UUID animalId,
        LocalDate date
) {}
