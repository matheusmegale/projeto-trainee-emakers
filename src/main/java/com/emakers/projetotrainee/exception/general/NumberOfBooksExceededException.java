package com.emakers.projetotrainee.exception.general;

public class NumberOfBooksExceededException extends RuntimeException {
    public NumberOfBooksExceededException(String mensagem) {
        super(mensagem);
    }
}
