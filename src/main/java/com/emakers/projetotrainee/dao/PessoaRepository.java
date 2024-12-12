package com.emakers.projetotrainee.dao;

import com.emakers.projetotrainee.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("SELECT p FROM Pessoa p WHERE p.email = :email AND p.senha = :senha")
    Pessoa findPessoaByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);

}
