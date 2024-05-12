package ru.edu.java.barbershop.api.exception;

public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException(String role) {
        super("Роли %s не существует".formatted(role));
    }
}
