package com.example.twitter;

import com.example.twitter.model.Role;
import com.example.twitter.model.User;
import com.example.twitter.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;

@SpringBootApplication
public class TwitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            userService.save(
                    User.builder()
                            .username("admin")
                            .password(encode().encode("1234"))
                            .isActive(true)
                            .roles(Collections.singleton(Role.ADMIN))
                            .build());
            userService.save(
                    User.builder()
                            .username("user")
                            .password(encode().encode("1234"))
                            .isActive(true)
                            .roles(Collections.singleton(Role.USER))
                            .build());
        };
    }

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

}
