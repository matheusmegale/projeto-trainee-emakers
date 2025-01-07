package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.data.dto.request.EmprestimoLivroRequestDTO;
import com.emakers.projetotrainee.data.dto.request.PessoaRequestDTO;
import com.emakers.projetotrainee.data.dto.response.PessoaResponseDTO;
import com.emakers.projetotrainee.service.PessoaService;
import com.emakers.projetotrainee.viacep.EnderecoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pessoa", description = "Endpoints relacionados à área de pessoa")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Lista todas as pessoas do sistema",
            description = "Obtém a lista completa de pessoas cadastradas no sistema.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value = "/all",
    produces = "aplication/json")
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getAllPessoas());
    }

    @Operation(summary = "Obtém os dados de uma pessoa pelo ID",
            description = "Retorna os dados de uma pessoa especificada pelo ID fornecido.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value = "/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(
            @Parameter(description = "ID da pessoa a ser buscada", required = true)
            @PathVariable Long id_pessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getPessoaById(id_pessoa));
    }

    @Operation(summary = "Cria uma nova pessoa",
            description = "Recebe os dados de uma pessoa e a cria no sistema.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content),
            }
    )
    @PostMapping(value = "/create")
    public ResponseEntity<PessoaResponseDTO> createPessoa(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da pessoa a ser criada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PessoaRequestDTO.class))
            )
            @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.createPessoa(pessoaRequestDTO));
    }

    @Operation(summary = "Atualiza os dados de uma pessoa",
            description = "Atualiza os dados de uma pessoa no sistema com base no ID informado.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content),
            }
    )
    @PutMapping(value = "/update/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(
            @Parameter(description = "ID da pessoa a ser atualizada", required = true)
            @PathVariable Long id_pessoa,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da pessoa a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PessoaRequestDTO.class))
            )
            @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.updatePessoa(id_pessoa, pessoaRequestDTO));
    }

    @Operation(summary = "Associa um livro a uma pessoa",
            description = "Registra o empréstimo de um livro para uma pessoa específica, identificada pelo ID fornecido.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content),
            }
    )
    @PatchMapping(value = "/pegarLivro/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> pegarLivro(
            @Parameter(description = "ID da pessoa que irá pegar o livro", required = true)
            @PathVariable Long id_pessoa,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informações do livro a ser emprestado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmprestimoLivroRequestDTO.class))
            )
            @Valid @RequestBody EmprestimoLivroRequestDTO emprestimoLivroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.pegarLivro(id_pessoa, emprestimoLivroRequestDTO));
    }

    @Operation(summary = "Devolve um livro associado a uma pessoa",
            description = "Registra a devolução de um livro previamente emprestado por uma pessoa específica, identificada pelo ID fornecido.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content),
            }
    )
    @PatchMapping(value = "/devolverLivro/{id_pessoa}")
    public ResponseEntity<PessoaResponseDTO> devolverLivro(
            @Parameter(description = "ID da pessoa que está devolvendo o livro", required = true)
            @PathVariable Long id_pessoa,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informações do livro a ser devolvido",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmprestimoLivroRequestDTO.class))
            )
            @Valid @RequestBody EmprestimoLivroRequestDTO emprestimoLivroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.devolverLivro(id_pessoa, emprestimoLivroRequestDTO));
    }

    @Operation(summary = "Deleta uma pessoa do sistema",
            description = "Remove uma pessoa específica do sistema com base no ID fornecido.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "Pessoa deletada com sucesso"))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content),
            }
    )
    @DeleteMapping(value = "/delete/{id_pessoa}")
    public ResponseEntity<String> deletePessoa(
            @Parameter(description = "ID da pessoa a ser deletada", required = true)
            @PathVariable Long id_pessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletePessoa(id_pessoa));
    }

//    @GetMapping
//    public ResponseEntity<String> getUser() {
//        return ResponseEntity.ok("Você está no sistema!");
//    }

    // metodo para utilizar api externa (ViaCep)
    @Operation(summary = "Consulta o endereço de uma pessoa",
            description = "Obtém o endereço de uma pessoa específica com base no ID fornecido.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnderecoResponse.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content),
            }
    )
    @GetMapping(value = "/endereco/{id_pessoa}")
    public ResponseEntity getEndereco(
            @Parameter(description = "ID da pessoa cujo endereço será consultado", required = true)
            @PathVariable Long id_pessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getEndereco(id_pessoa));
    }
}
