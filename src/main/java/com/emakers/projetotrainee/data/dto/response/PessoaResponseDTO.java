package com.emakers.projetotrainee.data.dto.response;

import com.emakers.projetotrainee.data.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record PessoaResponseDTO(

        Long id_pessoa,

        String nome,

        String cep,

        String email,

        String senha,

        List<LivroResponseDTO> livros
) {
    public PessoaResponseDTO(Pessoa pessoa) {
        this(pessoa.getId_pessoa(), pessoa.getNome(), pessoa.getCep(), pessoa.getEmail(), pessoa.getSenha(), pessoa.getLivros().stream().map(LivroResponseDTO::new).collect(Collectors.toList()));
    }
}
