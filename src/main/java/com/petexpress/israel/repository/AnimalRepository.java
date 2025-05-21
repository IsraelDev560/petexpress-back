package com.petexpress.israel.repository;

import com.petexpress.israel.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    Optional<Animal> findByName(String name);
    Optional<Animal> findBySpecie(String specie);
}
