package com.petexpress.israel.service;

import com.petexpress.israel.dto.update.UserUpdateDto;
import com.petexpress.israel.entities.User;
import com.petexpress.israel.exceptions.UserExceptions;
import com.petexpress.israel.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if (this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserExceptions.ResourceAlreadyExistsException(
                    "Usuário com e-mail " + user.getEmail() + " já cadastrado."
            );
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(UUID id, UserUpdateDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserExceptions.ResourceNotFoundException("Usuario não encontrado" + id));
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setRole(User.Role.valueOf(dto.getRole()));
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
