package com.emakers.projetotrainee.service;

import com.emakers.projetotrainee.data.dto.request.LivroRequestDTO;
import com.emakers.projetotrainee.data.dto.response.LivroResponseDTO;
import com.emakers.projetotrainee.data.entity.Livro;
import com.emakers.projetotrainee.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<LivroResponseDTO> getAllLivros() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream().map(LivroResponseDTO::new).collect(Collectors.toList());
    }

    public LivroResponseDTO getLivroById(Long id_livro) {
        Livro livro = getLivroEntityById(id_livro);
        return new LivroResponseDTO(livro);
    }

    public LivroResponseDTO createLivro(LivroRequestDTO livroRequestDTO) {
        Livro livro = new Livro(livroRequestDTO);
        livroRepository.save(livro);

        return new LivroResponseDTO(livro);
    }

    public LivroResponseDTO updateLivro(Long id_livro, LivroRequestDTO livroRequestDTO) {
        Livro livro = getLivroEntityById(id_livro);
        livro.setNome(livroRequestDTO.nome());
        livro.setAutor(livroRequestDTO.autor());
        livro.setData_lancamento(livroRequestDTO.data_lancamento());
        livro.setPessoas(livroRequestDTO.pessoas());
        livroRepository.save(livro);

        return new LivroResponseDTO(livro);
    }

    public String deleteLivro(Long id_livro) {
        Livro livro = getLivroEntityById(id_livro);
        livroRepository.delete(livro);

        return "Livro de id: " + id_livro + " deletado.";
    }

    private Livro getLivroEntityById(Long id_livro) {
        Livro livro = livroRepository.findById(id_livro).orElseThrow(()-> new RuntimeException("Livro nao encontrado"));
        return livro;
    }
}
