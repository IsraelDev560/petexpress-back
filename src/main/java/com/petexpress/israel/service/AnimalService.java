package com.petexpress.israel.service;

import com.petexpress.israel.dto.update.AnimalUpdateDto;
import com.petexpress.israel.entities.Animal;
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

    public List<Animal> getAllAnimals(){
        return animalRepository.findAll();
    }

    public Animal createAnimal(Animal animal){
        return animalRepository.save(animal);
    }

    public Animal getAnimalById(UUID id){
        return animalRepository.getById(id);
    }

    @Transactional
    public Animal updateAnimal(UUID id, AnimalUpdateDto dto) throws ChangeSetPersister.NotFoundException {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        animal.setName(dto.getName());
        animal.setSpecie(dto.getSpecie());
        return animalRepository.save(animal);
    }

    public void deleteAnimal(UUID id){
        animalRepository.deleteById(id);
    }
}
