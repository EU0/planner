package com.project.planner.service;

import com.project.planner.domain.User;

public interface UserService {
    User createUser(User user);
    User registerUser(User user);
    User updateUser(User user);
    boolean deleteUser(User user);
    boolean changePassword(User user, String newPassword);
}
