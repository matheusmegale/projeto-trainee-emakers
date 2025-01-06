package com.emakers.projetotrainee.exception.general;

public class DidNotGetBookException extends RuntimeException {
    public DidNotGetBookException(Long id) {
        super("Você não está com o livro de id " + id);
    }
}
