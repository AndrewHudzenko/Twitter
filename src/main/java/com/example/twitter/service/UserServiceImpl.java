package com.example.twitter.service;

import com.example.twitter.model.Role;
import com.example.twitter.model.User;
import com.example.twitter.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, MailService mailService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mailService = mailService;
    }

    @Override
    public boolean save(User user) {
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            return false;
        }
        String password = user.getPassword();

        user.setActive(true);
        user.setPassword(encoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (StringUtils.hasText(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Twitter. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailService.send(user.getEmail(),
                    "Activation Code",
                    message);
        }

        return true;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }
}
