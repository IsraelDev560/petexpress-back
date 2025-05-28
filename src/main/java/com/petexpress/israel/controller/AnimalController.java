package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.AnimalRequestDto;
import com.petexpress.israel.dto.res.AnimalResponseDto;
import com.petexpress.israel.entities.Animal;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.AnimalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("animals")
@Tag(name = "Animals")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping
    public ResponseEntity<List<AnimalResponseDto>> getAllAnimals(){
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @PostMapping
    public ResponseEntity<AnimalResponseDto> createAnimal(@RequestBody @Valid AnimalRequestDto dto){
        Animal animal = animalService.createAnimal(dto);
        return ResponseEntity.ok(new AnimalResponseDto(
                animal.getId(),
                animal.getName(),
                animal.getSpecie()
        ));
    }
}
