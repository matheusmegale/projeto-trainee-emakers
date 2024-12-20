package com.emakers.projetotrainee.service;

import com.emakers.projetotrainee.data.dto.request.PessoaRequestDTO;
import com.emakers.projetotrainee.data.dto.response.PessoaResponseDTO;
import com.emakers.projetotrainee.data.entity.Pessoa;
import com.emakers.projetotrainee.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaResponseDTO> getAllPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList());
    }

    public PessoaResponseDTO getPessoaById(Long id_pessoa) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);

        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO createPessoa(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = new Pessoa(pessoaRequestDTO);
        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO updatePessoa(Long id_pessoa, PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCep(pessoaRequestDTO.cep());
        pessoa.setEmail(pessoaRequestDTO.email());
        pessoa.setSenha(pessoaRequestDTO.senha());
        pessoa.setLivros(pessoaRequestDTO.livros());
        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa);
    }

    public String deletePessoa(Long id_pessoa) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);
        pessoaRepository.delete(pessoa);

        return "Pessoa de id: " + id_pessoa + " deletada.";
    }

    private Pessoa getPessoaEntityById(Long id_pessoa) {
        Pessoa pessoa = pessoaRepository.findById(id_pessoa).orElseThrow(()-> new RuntimeException("Pessoa nao encontrada"));
        return pessoa;
    }
}
