package com.emakers.projetotrainee.data.entity;

import com.emakers.projetotrainee.data.dto.request.LivroRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_livro")
    private Long id_livro;

    @Column(name="nome", nullable = false, length = 45)
    private String nome;

    @Column(name="autor", nullable = false, length = 45)
    private String autor;

    @Column(name="data_lancamento", nullable = false, length = 20)
    private String data_lancamento;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "emprestimo",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_pessoa")
    )
    private List<Pessoa> pessoas;

    // metodo para adicionar uma pessoa à lista
    public void addPessoa(Pessoa pessoa) {
        if (pessoas == null) {
            pessoas = new ArrayList<>();
        }
        pessoas.add(pessoa);
    }

    @Builder
    public Livro(LivroRequestDTO livroRequestDTO) {
        this.nome = livroRequestDTO.nome();
        this.autor = livroRequestDTO.autor();
        this.data_lancamento = livroRequestDTO.data_lancamento();
        this.pessoas = livroRequestDTO.pessoas();
    }
}

