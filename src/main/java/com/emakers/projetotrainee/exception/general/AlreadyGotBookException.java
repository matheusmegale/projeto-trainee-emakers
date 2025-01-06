package com.emakers.projetotrainee.exception.general;

public class AlreadyGotBookException extends RuntimeException {

    public AlreadyGotBookException(Long id) {
        super("Você já tem o livro de id " + id);
    }

}
