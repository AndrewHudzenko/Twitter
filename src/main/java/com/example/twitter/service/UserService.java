package com.example.twitter.service;

import com.example.twitter.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean save(User user);

    Optional<User> findUserByUsername(String username);

    List<User> findAll();

    boolean activateUser(String code);

    void save(User user, String username, Map<String, String> form);

    void updateProfile(User user, String password, String email);
}
