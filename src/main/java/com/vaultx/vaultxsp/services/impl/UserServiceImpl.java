package com.vaultx.vaultxsp.services.impl;

import com.vaultx.vaultxsp.models.User;
import com.vaultx.vaultxsp.repositories.UserRepository;
import com.vaultx.vaultxsp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserid(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User updateUser(String id, User user) {
        if (!id.equals(user.getUserid())) {
            throw new IllegalArgumentException("ID mismatch");
        }
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsById(user.getUserid())) {
            throw new RuntimeException("User already exists with id: " + user.getUserid());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
