package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.AnimalRequestDto;
import com.petexpress.israel.dto.res.AnimalResponseDto;
import com.petexpress.israel.dto.update.AnimalUpdateDto;
import com.petexpress.israel.entities.Animal;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Animals", description = "Manage animal records in the system")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Operation(summary = "Get all animals", description = "Returns a list of all animals registered in the system.")
    @GetMapping
    public ResponseEntity<List<AnimalResponseDto>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @Operation(summary = "Get animal by ID", description = "Fetch an animal's information using its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDto> getAnimalById(@PathVariable UUID id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @Operation(summary = "Create a new animal", description = "Registers a new animal and returns its details.")
    @PostMapping
    public ResponseEntity<AnimalResponseDto> createAnimal(@RequestBody @Valid AnimalRequestDto dto) {
        Animal animal = animalService.createAnimal(dto);
        return ResponseEntity.ok(new AnimalResponseDto(
                animal.getId(),
                animal.getName(),
                animal.getSpecie()
        ));
    }

    @Operation(summary = "Update animal by ID", description = "Updates the information of an existing animal using its ID.")
    @PatchMapping("/{id}")
    public ResponseEntity<AnimalResponseDto> updateAnimal(@PathVariable UUID id, @RequestBody @Valid AnimalUpdateDto dto) throws ChangeSetPersister.NotFoundException {
        Animal animal = animalService.updateAnimal(id, dto);
        return ResponseEntity.ok(new AnimalResponseDto(animal.getId(), animal.getName(), animal.getSpecie()));
    }

    @Operation(summary = "Delete animal by ID", description = "Removes an animal record from the system by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
