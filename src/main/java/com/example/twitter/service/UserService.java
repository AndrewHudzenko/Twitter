package com.example.twitter.service;

import com.example.twitter.model.User;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findUserByUsername(String username);

}
