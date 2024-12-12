package com.emakers.projetotrainee.service;

import com.emakers.projetotrainee.dao.PessoaRepository;
import com.emakers.projetotrainee.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Override
    public Pessoa findById(int id) {
        Optional<Pessoa> result = pessoaRepository.findById(id);

        Pessoa pessoa = null;

        if (result.isPresent()) {
            pessoa = result.get();
        }
        else {
            // pessoa nao encontrada
            throw new RuntimeException("Pessoa de id " + id + " nao encontrada.");
        }

        return pessoa;
    }

    @Override
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public void deleteById(int id) {
        pessoaRepository.deleteById(id);
    }

    @Override
    public Pessoa findPessoaByEmailAndSenha(String email, String senha) {
        return pessoaRepository.findPessoaByEmailAndSenha(email, senha);
    }
}
