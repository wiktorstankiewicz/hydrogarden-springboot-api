package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.reporitiries.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nullable
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
