package com.emakers.projetotrainee.entity;

import jakarta.persistence.*;

@Entity
@Table(name="pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pessoa")
    private int id;

    @Column(name="nome")
    private String nome;

    @Column(name="cep")
    private String cep;

    @Column(name="email")
    private String email;

    @Column(name="senha")
    private String senha;

    public Pessoa() {

    }

    public Pessoa(String nome, String cep, String email, String senha) {
        this.nome = nome;
        this.cep = cep;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
