package com.emakers.projetotrainee.data.dto.request;

import com.emakers.projetotrainee.data.entity.Livro;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PessoaRequestDTO(

        @NotBlank(message = "Nome eh necessario")
        String nome,

        @NotBlank(message = "CEP eh necessario")
        String cep,

        @NotBlank(message = "Email eh necessario")
        String email,

        @NotBlank(message = "Senha eh necessaria")
        String senha,

        List<Livro> livros
) {
}
