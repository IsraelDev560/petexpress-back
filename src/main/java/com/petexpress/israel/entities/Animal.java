package com.petexpress.israel.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "animals")
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String specie;

    @OneToMany(mappedBy = "task")
    private List<Task> tasksList;
}
