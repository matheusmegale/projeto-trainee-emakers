package com.emakers.projetotrainee.data.dto.response;

import com.emakers.projetotrainee.data.entity.Livro;

public record LivroResponseDTO(

        Long id_livro,

        String nome,

        String autor,

        String data_lancamento
) {
    public LivroResponseDTO(Livro livro) {
        this(livro.getId_livro(), livro.getNome(), livro.getAutor(), livro.getData_lancamento());
    }
}
