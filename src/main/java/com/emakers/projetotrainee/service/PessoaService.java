package com.emakers.projetotrainee.service;

import com.emakers.projetotrainee.data.dto.request.EmprestimoLivroRequestDTO;
import com.emakers.projetotrainee.data.dto.request.PessoaRequestDTO;
import com.emakers.projetotrainee.data.dto.response.PessoaResponseDTO;
import com.emakers.projetotrainee.data.entity.Livro;
import com.emakers.projetotrainee.data.entity.Pessoa;
import com.emakers.projetotrainee.exception.general.*;
import com.emakers.projetotrainee.repository.LivroRepository;
import com.emakers.projetotrainee.repository.PessoaRepository;
import com.emakers.projetotrainee.viacep.EnderecoFeign;
import com.emakers.projetotrainee.viacep.EnderecoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EnderecoFeign enderecoFeign;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        //pessoa.setSenha(pessoaRequestDTO.senha());
        pessoa.setSenha(passwordEncoder.encode(pessoaRequestDTO.senha()));
        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO pegarLivro(Long id_pessoa, EmprestimoLivroRequestDTO emprestimoLivroRequestDTO) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa); // busca a pessoa pelo id

        Livro livro = livroRepository.findById(emprestimoLivroRequestDTO.id_livro())
                .orElseThrow(() -> new EntityNotFoundException(emprestimoLivroRequestDTO.id_livro())); // busca o livro pelo id

        if (pessoa.getLivros().size() >= 3) {
            throw new NumberOfBooksExceededException("Você já tem 3 livros");
        }

        // Verifica se a relação já existe para evitar duplicação
        if (!pessoa.getLivros().contains(livro)) {
            pessoa.getLivros().add(livro); // Adiciona o livro à pessoa
            livro.getPessoas().add(pessoa); // Adiciona a pessoa ao livro
            pessoaRepository.save(pessoa); // Salva a pessoa para persistir a relação
        } else {
            throw new AlreadyGotBookException(emprestimoLivroRequestDTO.id_livro());
        }

        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO devolverLivro(Long id_pessoa, EmprestimoLivroRequestDTO emprestimoLivroRequestDTO) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);

        Livro livro = livroRepository.findById(emprestimoLivroRequestDTO.id_livro())
                .orElseThrow(() -> new EntityNotFoundException(emprestimoLivroRequestDTO.id_livro())); // busca o livro pelo id

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
        } else {
            throw new DidNotGetBookException(emprestimoLivroRequestDTO.id_livro());
        }

        return new PessoaResponseDTO(pessoa);
    }

    public String deletePessoa(Long id_pessoa) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);
        pessoaRepository.delete(pessoa);

        return "Pessoa de id: " + id_pessoa + " deletada.";
    }

    public EnderecoResponse getEndereco(Long id_pessoa) {
        Pessoa pessoa = getPessoaEntityById(id_pessoa);
        EnderecoResponse enderecoResponse = enderecoFeign.getEnderecoWithViacep(pessoa.getCep());

        if (enderecoResponse == null || isEnderecoResponseInvalido(enderecoResponse)) {
            throw new CepInvalidoException("CEP inválido: " + pessoa.getCep());
        }

        return enderecoResponse;
    }

    private boolean isEnderecoResponseInvalido(EnderecoResponse enderecoResponse) {
        return enderecoResponse.getLogradouro() == null || enderecoResponse.getBairro() == null;
    }

    private Pessoa getPessoaEntityById(Long id_pessoa) {
        Pessoa pessoa = pessoaRepository.findById(id_pessoa).orElseThrow(()-> new EntityNotFoundException(id_pessoa));
        return pessoa;
    }
}
