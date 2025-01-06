package com.emakers.projetotrainee.exception.general;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(Long id) {
        super("Entidade de id " + id + " nao encontrada");
    }
}
