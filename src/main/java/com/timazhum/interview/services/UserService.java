package com.timazhum.interview.services;

import com.timazhum.interview.models.Role;
import com.timazhum.interview.models.User;
import com.timazhum.interview.models.UserPrincipal;
import com.timazhum.interview.repositories.RoleRepository;
import com.timazhum.interview.repositories.UserRepository;
import com.timazhum.interview.viewmodels.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            return new UserPrincipal(optionalUser.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public Optional<User> tryRegisterUser(@Valid UserRegistrationDto userRegistrationDto, BindingResult result) {
        if (userRegistrationDto == null) return Optional.empty();

        Optional<User> optionalUser = userRepository.findByUsername(userRegistrationDto.getUsername());
        if (optionalUser.isPresent()) {
            result.rejectValue("username", null, "There is already an account registered with that username");
            return Optional.empty();
        }

        optionalUser = userRepository.findByEmail(userRegistrationDto.getEmail());
        if (optionalUser.isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
            return Optional.empty();
        }

        if (result.hasErrors()) {
            return Optional.empty();
        }

        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setActive(true);
        user.setEmailConfirmed(true);
        user.setCreatedAt(LocalDateTime.now());

        Optional<Role> optionalRole = roleRepository.findByName(Role.RoleName.ROLE_USER.name());
        Role role = optionalRole.orElseGet(() -> new Role(Role.RoleName.ROLE_USER.name()));

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);

        return Optional.of(user);
    }
}
