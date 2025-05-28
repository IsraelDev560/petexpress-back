package com.petexpress.israel.service;

import com.petexpress.israel.dto.res.UserResponseDto;
import com.petexpress.israel.dto.update.UserUpdateDto;
import com.petexpress.israel.entities.User;
import com.petexpress.israel.exceptions.UserExceptions;
import com.petexpress.israel.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User data) {
        if (this.userRepository.findByUsername(data.getUsername()) != null) {
            throw new UserExceptions.ResourceAlreadyExistsException(
                    "Usuário com username já cadastrado."
            );
        }
        return userRepository.save(data);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrada com ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(UUID id, UserUpdateDto dto) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setUsername(dto.username());
            user.setRole(dto.role());
            return userRepository.save(user);
        } catch (UserExceptions.ResourceNotFoundException exception) {
            throw new UserExceptions.ResourceNotFoundException("Usuario não encontrado");
        }
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
