package com.restAPI.banking_app.service;

import com.restAPI.banking_app.entity.User;

import java.util.List;

public interface UserService {

    User addUser(User user);
    User getUserById(Long id);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);

    List<User> getAllUsers();
}
