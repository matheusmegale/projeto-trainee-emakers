package com.emakers.projetotrainee.exception.general;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super("Email não encontrado");
    }
}
