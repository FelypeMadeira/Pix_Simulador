package com.example.Pix.controller;


import com.example.Pix.model.TransacaoEntidade;
import com.example.Pix.repository.TransacaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pix")
public class PixController {
    private final TransacaoRepository repository;

    public PixController(TransacaoRepository repository){
        this.repository = repository;

    }
    @PostMapping("/enviar")
    public TransacaoEntidade enviarPix(@RequestBody TransacaoEntidade t) {
        return repository.save(t);
    }

    @GetMapping("/listar")
    public List<TransacaoEntidade> listar(){
        return repository.findAll();
    }
}
