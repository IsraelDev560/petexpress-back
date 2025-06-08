package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.AnimalRequestDto;
import com.petexpress.israel.dto.res.AnimalResponseDto;
import com.petexpress.israel.dto.update.AnimalUpdateDto;
import com.petexpress.israel.entities.Animal;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.AnimalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("animals")
@Tag(name = "Animals")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping
    public ResponseEntity<List<AnimalResponseDto>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDto> getAnimalById(@PathVariable UUID id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @PostMapping
    public ResponseEntity<AnimalResponseDto> createAnimal(@RequestBody @Valid AnimalRequestDto dto) {
        Animal animal = animalService.createAnimal(dto);
        return ResponseEntity.ok(new AnimalResponseDto(
                animal.getId(),
                animal.getName(),
                animal.getSpecie()
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnimalResponseDto> updateAnimal(@PathVariable UUID id, @RequestBody @Valid AnimalUpdateDto dto) throws ChangeSetPersister.NotFoundException {
        Animal animal = animalService.updateAnimal(id, dto);
        return ResponseEntity.ok(new AnimalResponseDto(animal.getId(), animal.getName(), animal.getSpecie()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
