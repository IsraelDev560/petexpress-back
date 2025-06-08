package com.petexpress.israel.service;

import com.petexpress.israel.dto.request.AnimalRequestDto;
import com.petexpress.israel.dto.res.AnimalResponseDto;
import com.petexpress.israel.dto.update.AnimalUpdateDto;
import com.petexpress.israel.entities.Animal;
import com.petexpress.israel.exceptions.UserExceptions;
import com.petexpress.israel.repository.AnimalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<AnimalResponseDto> getAllAnimals() {
        return animalRepository.findAll()
                .stream()
                .map(animal -> new AnimalResponseDto(
                        animal.getId(),
                        animal.getName(),
                        animal.getSpecie()))
                .toList();
    }

    public Animal createAnimal(AnimalRequestDto dto) {
        if (animalRepository.findByName(dto.name()).isPresent()) {
            throw new UserExceptions.ResourceAlreadyExistsException(
                    "Animal com nome já cadastrado."
            );
        }

        Animal animal = new Animal();
        animal.setName(dto.name());
        animal.setSpecie(dto.specie());

        return animalRepository.save(animal);
    }

    public AnimalResponseDto getAnimalById(UUID id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal não encontrado com ID:" + id));
        return new AnimalResponseDto(animal.getId(), animal.getName(), animal.getSpecie());
    }

    @Transactional
    public Animal updateAnimal(UUID id, AnimalUpdateDto dto) throws ChangeSetPersister.NotFoundException {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        animal.setName(dto.name());
        animal.setSpecie(dto.name());
        return animalRepository.save(animal);
    }

    public void deleteAnimal(UUID id) {
        animalRepository.deleteById(id);
    }
}
