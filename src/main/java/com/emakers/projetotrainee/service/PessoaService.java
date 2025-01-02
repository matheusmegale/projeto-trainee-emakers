package com.emakers.projetotrainee.service;

import com.emakers.projetotrainee.data.dto.request.EmprestarLivroRequestDTO;
import com.emakers.projetotrainee.data.dto.request.LivroRequestDTO;
import com.emakers.projetotrainee.data.dto.request.PessoaRequestDTO;
import com.emakers.projetotrainee.data.dto.response.LivroResponseDTO;
import com.emakers.projetotrainee.data.dto.response.PessoaResponseDTO;
import com.emakers.projetotrainee.data.entity.Livro;
import com.emakers.projetotrainee.data.entity.Pessoa;
import com.emakers.projetotrainee.repository.LivroRepository;
import com.emakers.projetotrainee.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LivroRepository livroRepository;

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
        // pessoa.setLivros(pessoaRequestDTO.livros());
        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO pegarLivro(Long id_pessoa, EmprestarLivroRequestDTO emprestarLivroRequestDTO) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa); // busca a pessoa pelo id

        Livro livro = livroRepository.findById(emprestarLivroRequestDTO.id_livro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado")); // busca o livro pelo id

        System.out.println("CHEGUEI AQUI, ENCONTREI A PESSOA E O LIVRO NO BD");

        // Verifica se a relação já existe para evitar duplicação
        if (!pessoa.getLivros().contains(livro)) {
            pessoa.getLivros().add(livro); // Adiciona o livro à pessoa
            System.out.println("Adicionando o livro na lista da pessoa");
            livro.getPessoas().add(pessoa); // Adiciona a pessoa ao livro
            System.out.println("Adicionando a pessoa na lista do livro");
            pessoaRepository.save(pessoa); // Salva a pessoa para persistir a relação
            System.out.println("Salvando a pessoa");
            //livroRepository.save(livro); // Salva o livro para garantir consistência
            //System.out.println("Salvando o livro");
            System.out.println("Pessoas do livro de id " + livro.getId_livro());
            for(int i = 0; i < livro.getPessoas().size(); i++) {
                System.out.println(livro.getPessoas().get(i).getNome());
            }
        } else {
            throw new IllegalArgumentException("A relação entre o livro e a pessoa já existe.");
        }

        // Monta a resposta
        // A resposta deve ser retornada desta maneira porque estava dando problema de serialização
        // Quando uma pessoa pegava um livro emprestado, a relação era adicionada à tabela emprestimo
        // corretamente, porém, ao retornar o objeto PessoaResponseDTO, dava erro de serialização.
        // As anotações @JsonManagedReference e @JsonBackReference, que são usadas para resolver
        // o problema de serialização, estavam causando erro no CRUD
//        List<LivroResponseDTO> livrosDto = pessoa.getLivros().stream()
//                .map(l -> new LivroResponseDTO(l.getId_livro(), l.getNome(), l.getAutor(), l.getData_lancamento()))
//                .collect(Collectors.toList());
//
//        return new PessoaResponseDTO(pessoa.getId_pessoa(), pessoa.getNome(), pessoa.getCep(), pessoa.getEmail(), pessoa.getSenha(), livrosDto);
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO devolverLivro(Long id_pessoa, EmprestarLivroRequestDTO emprestarLivroRequestDTO) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);

        Livro livro = livroRepository.findById(emprestarLivroRequestDTO.id_livro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado")); // busca o livro pelo id

        if(pessoa.getLivros().contains(livro)) {
            for(int i = 0; i < pessoa.getLivros().size(); i++) {
                if(pessoa.getLivros().get(i) == livro) {
                    pessoa.getLivros().remove(i);
                }
            }
            pessoaRepository.save(pessoa);

            for(int i = 0; i < livro.getPessoas().size(); i++) {
                if(livro.getPessoas().get(i) == pessoa) {
                    livro.getPessoas().remove(i);
                }
            }
            livroRepository.save(livro);
            System.out.println("Pessoas do livro de id " + livro.getId_livro());
            for(int i = 0; i < livro.getPessoas().size(); i++) {
                System.out.println(livro.getPessoas().get(i).getNome());
            }
        } else {
            throw new IllegalArgumentException("Você não está com esse livro.");
        }

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
