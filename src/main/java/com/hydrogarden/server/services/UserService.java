package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.reporitiries.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nullable
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User createUser(User userToCreate) {
        // todo tu jakos musimy zwalidowac zeby nie presyłać technicznego id i version
        return userRepository.save(userToCreate);
    }

    public User updateUser(User userToCreate) {
        Objects.requireNonNull(userToCreate.getId());
        Objects.requireNonNull(userToCreate.getVersion());
        // todo a tu wymagamy id i version
        return userRepository.save(userToCreate);
    }
}
