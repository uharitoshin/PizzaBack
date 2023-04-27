package com.example.pizza.security.runner;

import com.example.pizza.security.model.Role;
import com.example.pizza.security.model.Status;
import com.example.pizza.security.model.User;
import com.example.pizza.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class InitStartSettings implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            userRepository.saveAll(createDefaultUsers());
        }
    }

    private List<User> createDefaultUsers() {
        User user = new User();
        user.setLogin("user@mail.ru");
        user.setPassword(new BCryptPasswordEncoder(12).encode("user"));
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setRole(Role.ROLE_USER);
        user.setStatus(Status.ACTIVE);

        User admin = new User();
        admin.setLogin("admin@mail.ru");
        admin.setPassword(new BCryptPasswordEncoder(12).encode("admin"));
        admin.setFirstName("Petr");
        admin.setLastName("Petrov");
        admin.setRole(Role.ROLE_ADMIN);
        admin.setStatus(Status.ACTIVE);

        return List.of(user, admin);
    }
}
