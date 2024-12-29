package com.emakers.projetotrainee.data.dto.response;

import com.emakers.projetotrainee.data.entity.Livro;
import com.emakers.projetotrainee.data.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

public record LivroResponseDTO(

        Long id_livro,

        String nome,

        String autor,

        String data_lancamento,

        //List<Pessoa> pessoas
        List<PessoaResponseDTO> pessoas
) {
//    public LivroResponseDTO(Livro livro) {
//        this(livro.getId_livro(), livro.getNome(), livro.getAutor(), livro.getData_lancamento(), livro.getPessoas());
//    }

    public LivroResponseDTO(Livro livro) {
        this(livro.getId_livro(), livro.getNome(), livro.getAutor(), livro.getData_lancamento(), livro.getPessoas().stream().map(PessoaResponseDTO::new).collect(Collectors.toList()));
    }

    // um construtor alternativo que não passa a lista de pessoas (para nao poluir a resposta de uma pessoa)
    public LivroResponseDTO(Long id_livro, String nome, String autor, String data_lancamento) {
        this(id_livro, nome, autor, data_lancamento, null);
    }
}
