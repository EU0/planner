package com.project.planner.service.impl;

import com.project.planner.constants.AuthorityConstants;
import com.project.planner.domain.User;
import com.project.planner.repository.AuthorityRepository;
import com.project.planner.repository.UserRepository;
import com.project.planner.security.exception.EmailAlreadyUsedException;
import com.project.planner.security.exception.LoginAlreadyUsedException;
import com.project.planner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User registerUser(User user) {
        if (Objects.nonNull(user.getAuthoritySet())
                && user.getAuthoritySet().isEmpty()) {
            user.setAuthoritySet(authorityRepository
                    .findByName(AuthorityConstants.USER.name())
                    .stream()
                    .collect(Collectors.toSet()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(true);
        user.setLogin(user.getLogin().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());

        if (userRepository.existsByLoginIgnoreCase(user.getLogin())) {
            throw new LoginAlreadyUsedException();
        }
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        User savedUser = userRepository.save(user);

        log.debug("Создан новый пользователь: {}", savedUser);

        return savedUser;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        return false;
    }
}
