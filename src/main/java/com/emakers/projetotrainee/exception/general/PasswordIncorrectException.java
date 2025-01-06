package com.emakers.projetotrainee.exception.general;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException() {
        super("Senha incorreta");
    }
}
