package com.emakers.projetotrainee.data.dto.response;

import com.emakers.projetotrainee.data.entity.Livro;
import com.emakers.projetotrainee.data.entity.Pessoa;

import java.util.List;

public record LivroResponseDTO(

        Long id_livro,

        String nome,

        String autor,

        String data_lancamento,

        List<Pessoa> pessoas
) {
    public LivroResponseDTO(Livro livro) {
        this(livro.getId_livro(), livro.getNome(), livro.getAutor(), livro.getData_lancamento(), livro.getPessoas());
    }
}
