package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.data.dto.request.LivroRequestDTO;
import com.emakers.projetotrainee.data.dto.response.LivroResponseDTO;
import com.emakers.projetotrainee.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<LivroResponseDTO>> getAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getAllLivros());
    }

    @GetMapping(value = "/{id_livro}")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable Long id_livro) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getLivroById(id_livro));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<LivroResponseDTO> createLivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.createLivro(livroRequestDTO));
    }

    @PutMapping(value = "/update/{id_livro}")
    public ResponseEntity<LivroResponseDTO> updateLivro(@PathVariable Long id_livro,@Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.updateLivro(id_livro, livroRequestDTO));
    }

    @DeleteMapping(value = "/delete/{id_livro}")
    public ResponseEntity<String> deleteLivro(@PathVariable Long id_livro) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.deleteLivro(id_livro));
    }
}
