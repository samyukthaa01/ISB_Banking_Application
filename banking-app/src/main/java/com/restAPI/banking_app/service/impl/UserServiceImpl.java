package com.restAPI.banking_app.service.impl;

import com.restAPI.banking_app.ExceptionHandling.ApiException;
import com.restAPI.banking_app.entity.User;
import com.restAPI.banking_app.repository.UserRepo;
import com.restAPI.banking_app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found with id: " + id, "USER_NOT_FOUND"));
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found with id: " + id, "USER_NOT_FOUND"));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setAddress(userDetails.getAddress());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setStatus(userDetails.getStatus());
        user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found with id: " + id, "USER_NOT_FOUND"));
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Check if the list is empty and throw an exception if true
        if (users.isEmpty()) {
            throw new ApiException("No users found", "NO_USERS_FOUND");
        }

        // Return the list of users
        return users;
    }
}