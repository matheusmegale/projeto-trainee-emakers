package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.data.dto.request.LoginRequestDTO;
import com.emakers.projetotrainee.data.dto.request.RegisterRequestDTO;
import com.emakers.projetotrainee.data.dto.response.ResponseDTO;
import com.emakers.projetotrainee.data.entity.Pessoa;
import com.emakers.projetotrainee.exception.general.EmailNotFoundException;
import com.emakers.projetotrainee.exception.general.PasswordIncorrectException;
import com.emakers.projetotrainee.infra.security.TokenService;
import com.emakers.projetotrainee.repository.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Autenticação", description = "Endpoints relacionados à área de autenticação")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Operation(summary = "Realiza o login de um usuário",
            description = "Autentica o usuário no sistema utilizando email e senha. Retorna um token JWT em caso de sucesso.",
            tags = {"Autenticação"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login do usuário, incluindo email e senha.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequestDTO.class))
            )
            @RequestBody LoginRequestDTO body) {
        Pessoa pessoa = this.pessoaRepository.findByEmail(body.email())
                .orElseThrow(() -> new EmailNotFoundException());

        if (passwordEncoder.matches(body.senha(), pessoa.getSenha())) {
            String token = this.tokenService.generateToken(pessoa);
            return ResponseEntity.ok(new ResponseDTO(pessoa.getNome(), token));
        }

        throw new PasswordIncorrectException();
    }

    @Operation(summary = "Registra um novo usuário",
            description = "Cria um novo usuário no sistema com email, senha, nome e CEP. Retorna um token JWT em caso de sucesso.",
            tags = {"Autenticação"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/register")
    public ResponseEntity register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do usuário a ser registrado, incluindo email, senha, nome e CEP.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequestDTO.class))
            )
            @RequestBody RegisterRequestDTO body) {
        Optional<Pessoa> pessoa = this.pessoaRepository.findByEmail(body.email());

        if(pessoa.isEmpty()) {
            Pessoa newPessoa = new Pessoa();
            newPessoa.setSenha(passwordEncoder.encode(body.senha()));
            newPessoa.setEmail(body.email());
            newPessoa.setCep(body.cep());
            newPessoa.setNome(body.nome());
            this.pessoaRepository.save(newPessoa);

            String token = this.tokenService.generateToken(newPessoa);
            return ResponseEntity.ok(new ResponseDTO(newPessoa.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
