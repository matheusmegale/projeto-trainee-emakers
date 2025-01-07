package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.data.dto.request.LivroRequestDTO;
import com.emakers.projetotrainee.data.dto.response.LivroResponseDTO;
import com.emakers.projetotrainee.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

@Tag(name = "Livro", description = "Endpoints relacionados à área de livro")
@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(summary = "Lista todos os livros do sistema",
            description = "Obtém a lista completa de livros cadastrados no sistema.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDTO.class)))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value = "/all")
    public ResponseEntity<List<LivroResponseDTO>> getAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getAllLivros());
    }

    @Operation(summary = "Obtém os detalhes de um livro específico",
            description = "Recupera as informações completas de um livro a partir do seu ID.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value = "/{id_livro}")
    public ResponseEntity<LivroResponseDTO> getLivroById(
            @Parameter(description = "ID do livro a ser buscado", required = true)
            @PathVariable Long id_livro) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getLivroById(id_livro));
    }

    @Operation(summary = "Cria um novo livro no sistema",
            description = "Cria um novo livro a partir dos dados fornecidos.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping(value = "/create")
    public ResponseEntity<LivroResponseDTO> createLivro(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do livro a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LivroRequestDTO.class))
            )
            @Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.createLivro(livroRequestDTO));
    }

    @Operation(summary = "Atualiza um livro no sistema",
            description = "Atualiza os dados de um livro existente no sistema com base nos dados fornecidos.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PutMapping(value = "/update/{id_livro}")
    public ResponseEntity<LivroResponseDTO> updateLivro(
            @Parameter(description = "ID do livro a ser atualizado", required = true)
            @PathVariable Long id_livro,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do livro a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LivroRequestDTO.class))
            )
            @Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.updateLivro(id_livro, livroRequestDTO));
    }

    @Operation(summary = "Deleta um livro do sistema",
            description = "Remove um livro do sistema com base no id fornecido.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @DeleteMapping(value = "/delete/{id_livro}")
    public ResponseEntity<String> deleteLivro(
            @Parameter(description = "ID do livro a ser deletado", required = true)
            @PathVariable Long id_livro) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.deleteLivro(id_livro));
    }
}
