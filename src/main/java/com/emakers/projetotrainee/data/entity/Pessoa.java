package com.emakers.projetotrainee.data.entity;

import com.emakers.projetotrainee.data.dto.request.PessoaRequestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pessoa")
    private Long id_pessoa;

    @Column(name="nome", nullable = false, length = 45)
    private String nome;

    @Column(name="cep", nullable = true, length = 9)
    private String cep;

    @Column(name="email", nullable = false, length = 30)
    private String email;

    @Column(name="senha", nullable = false, length = 80)
    private String senha;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "emprestimo",
            joinColumns = @JoinColumn(name = "id_pessoa"),
            inverseJoinColumns = @JoinColumn(name = "id_livro")
    )
    //@JsonManagedReference
    private List<Livro> livros = new ArrayList<>();

    // metodo para adicionar um livro à lista
    public void addLivro(Livro livro) {
        if (livros == null) {
            livros = new ArrayList<>();
        }
        livros.add(livro);
    }

    @Builder
    public Pessoa(PessoaRequestDTO pessoaRequestDTO) {
        this.nome = pessoaRequestDTO.nome();
        this.cep = pessoaRequestDTO.cep();
        this.email = pessoaRequestDTO.email();
        this.senha = pessoaRequestDTO.senha();
        this.livros = pessoaRequestDTO.livros();
    }
}

