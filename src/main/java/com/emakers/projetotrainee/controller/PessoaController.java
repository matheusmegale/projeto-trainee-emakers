package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.data.dto.request.EmprestarLivroRequestDTO;
import com.emakers.projetotrainee.data.dto.request.PessoaRequestDTO;
import com.emakers.projetotrainee.data.dto.response.PessoaResponseDTO;
import com.emakers.projetotrainee.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getAllPessoas());
    }

    @GetMapping(value = "/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable Long id_pessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getPessoaById(id_pessoa));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PessoaResponseDTO> createPessoa(@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.createPessoa(pessoaRequestDTO));
    }

    @PutMapping(value = "/update/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long id_pessoa,@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.updatePessoa(id_pessoa, pessoaRequestDTO));
    }

    @PatchMapping(value = "/pegarLivro/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> pegarLivro(@PathVariable Long id_pessoa,@Valid @RequestBody EmprestarLivroRequestDTO emprestarLivroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.pegarLivro(id_pessoa, emprestarLivroRequestDTO));
    }

    @PatchMapping(value = "/devolverLivro/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> devolverLivro(@PathVariable Long id_pessoa,@Valid @RequestBody EmprestarLivroRequestDTO emprestarLivroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.devolverLivro(id_pessoa, emprestarLivroRequestDTO));
    }

    @DeleteMapping(value = "/delete/{id_pessoa}")
    public ResponseEntity<String> deletePessoa(@PathVariable Long id_pessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletePessoa(id_pessoa));
    }

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Você está no sistema!");
    }
}
