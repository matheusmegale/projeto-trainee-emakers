package com.emakers.projetotrainee.infra.security;

import com.emakers.projetotrainee.data.entity.Pessoa;
import com.emakers.projetotrainee.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pessoa pessoa = this.pessoaRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new org.springframework.security.core.userdetails.User(pessoa.getEmail(), pessoa.getSenha(), new ArrayList<>());
    }
}
