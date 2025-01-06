package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.data.dto.request.LoginRequestDTO;
import com.emakers.projetotrainee.data.dto.request.RegisterRequestDTO;
import com.emakers.projetotrainee.data.dto.response.ResponseDTO;
import com.emakers.projetotrainee.data.entity.Pessoa;
import com.emakers.projetotrainee.exception.general.EmailNotFoundException;
import com.emakers.projetotrainee.exception.general.PasswordIncorrectException;
import com.emakers.projetotrainee.infra.security.TokenService;
import com.emakers.projetotrainee.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
//        Pessoa pessoa = this.pessoaRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//        if(passwordEncoder.matches(body.senha(), pessoa.getSenha())) {
//            String token = this.tokenService.generateToken(pessoa);
//            return ResponseEntity.ok(new ResponseDTO(pessoa.getNome(), token)); // testar com PessoaResponseDTO depois
//        }
//        return ResponseEntity.badRequest().build(); // isso não mostrava a mensagem de exceção
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Pessoa pessoa = this.pessoaRepository.findByEmail(body.email())
                .orElseThrow(() -> new EmailNotFoundException());

        if (passwordEncoder.matches(body.senha(), pessoa.getSenha())) {
            String token = this.tokenService.generateToken(pessoa);
            return ResponseEntity.ok(new ResponseDTO(pessoa.getNome(), token));
        }
        // throw new RuntimeException("Senha incorreta");
        throw new PasswordIncorrectException();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
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
