package com.timazhum.interview;

import com.timazhum.interview.models.User;
import com.timazhum.interview.repositories.UserRepository;
import org.h2.engine.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void whenExistentUsernameIsProvided_thenUserIsRetrieved() {
        User user = new User("test", "test@test.com", "password", true, true, 0L, LocalDateTime.now(), new HashSet<>(Role.USER));
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findByUsername("test");
        assertThat(optionalUser).isNotEmpty();
    }

    @Test
    void whenNonExistentUsernameIsProvided_thenUserIsNotRetrieved() {
        User user = new User("user", "user@user.com", "password", true, true, 0L, LocalDateTime.now(), new HashSet<>(Role.USER));
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findByUsername("non-existing-user");
        assertThat(optionalUser).isEmpty();
    }

    @Test
    void whenExistentUserEmailIsProvided_thenUserIsRetrieved() {
        User user = new User("some_email", "some@email.com", "password", true, true, 0L, LocalDateTime.now(), new HashSet<>(Role.USER));
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findByEmail("some@email.com");
        assertThat(optionalUser).isNotEmpty();
    }

    @Test
    void whenNonExistentUserEmailIsProvided_thenUserIsNotRetrieved() {
        User user = new User("no_email", "no@email.com", "password", true, true, 0L, LocalDateTime.now(), new HashSet<>(Role.USER));
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findByUsername("test@test.com");
        assertThat(optionalUser).isEmpty();
    }
}
