package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.UserRepository;
import com.hydrogarden.server.exceptions.UsernameTakenException;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public User createUser(User userToCreate) throws UsernameTakenException {
        User user = userRepository.findByUsername(userToCreate.getUsername());
        if(user != null){
            throw new UsernameTakenException();
        }
        return userRepository.save(userToCreate);
    }

    public User updateUser(User userToCreate) {
        User user = userRepository.findByUsername(userToCreate.getUsername());
        return userRepository.save(userToCreate);
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if(username == null){
                    throw new UsernameNotFoundException("");
                }
                UserDetails userDetails = userRepository.findByUsername(username);
                if(userDetails == null){
                    throw new UsernameNotFoundException("");
                }
                return userDetails;
            }
        };
    }

    public Optional<User> findByUsername(String username) {
        try{
            return Optional.of(userRepository.findByUsername(username));
        }catch(Exception e){
            return Optional.empty();
        }
    }
}
