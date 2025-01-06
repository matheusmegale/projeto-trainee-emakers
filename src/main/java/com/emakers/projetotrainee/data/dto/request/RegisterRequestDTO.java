package com.emakers.projetotrainee.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(

        @NotBlank(message = "Nome eh necessario")
        String nome,
        String cep,

        @NotBlank(message = "Email eh necessario")
        String email,

        @NotBlank(message = "Senha eh necessaria")
        String senha) {
}
