package com.project.planner.security.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Не правильно задан пароль");
    }
}
