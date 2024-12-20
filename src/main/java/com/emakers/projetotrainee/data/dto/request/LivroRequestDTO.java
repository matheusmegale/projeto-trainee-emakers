package com.emakers.projetotrainee.data.dto.request;

import com.emakers.projetotrainee.data.entity.Pessoa;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record LivroRequestDTO(
        @NotBlank(message = "Nome eh necessario")
        String nome,

        @NotBlank(message = "Autor eh necessario")
        String autor,

        @NotBlank(message = "Data de lancamento eh necessaria")
        String data_lancamento,

        List<Pessoa> pessoas
) {
}
