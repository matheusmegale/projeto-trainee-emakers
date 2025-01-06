package com.emakers.projetotrainee.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank(message = "Email eh necessario")
        String email,

        @NotBlank(message = "Senha eh necessaria")
        String senha) {
}
