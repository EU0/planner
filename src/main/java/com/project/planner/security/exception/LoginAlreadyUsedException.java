package com.project.planner.security.exception;

public class LoginAlreadyUsedException extends RuntimeException {
    public LoginAlreadyUsedException() {
        super("Логин уже используется");
    }
}
