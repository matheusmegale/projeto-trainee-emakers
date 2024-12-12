package com.emakers.projetotrainee.controller;

import com.emakers.projetotrainee.entity.Pessoa;
import com.emakers.projetotrainee.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    private PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        Pessoa pessoa = new Pessoa();

        model.addAttribute("pessoa", pessoa);

        return "view/pessoa/login";
    }

    @GetMapping("/verificarLogin")
    public String verificarLogin(@ModelAttribute("pessoa") Pessoa pessoa) {
        Pessoa verificarPessoa = pessoaService.findPessoaByEmailAndSenha(pessoa.getEmail(), pessoa.getSenha());
        if (verificarPessoa == null) {
            // retornar erro, tratar exceção
        }

        return "view/pessoa/pagina-inicial";
    }

    @GetMapping("/formularioParaCadastro")
    public String formularioParaCadastro(Model model) {
        Pessoa pessoa = new Pessoa();

        model.addAttribute("pessoa", pessoa);

        return "view/pessoa/formulario-pessoa";
    }

    @PostMapping("/cadastrar")
    public String cadastrarPessoa(@ModelAttribute("pessoa") Pessoa pessoa) {
        pessoaService.save(pessoa);

        return "view/pessoa/pagina-inicial";
    }

}
