package com.emakers.projetotrainee.service;

import com.emakers.projetotrainee.entity.Pessoa;

import java.util.List;

public interface PessoaService {

    List<Pessoa> findAll();

    Pessoa findById(int id);

    Pessoa save(Pessoa pessoa);

    void deleteById(int id);

    Pessoa findPessoaByEmailAndSenha(String email, String senha);

}
