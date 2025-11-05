package com.example.Pix.controller;


import com.example.Pix.dto.TransacaoDTORequisicao;
import com.example.Pix.dto.TransacaoDTOResposta;
import com.example.Pix.model.TransacaoEntidade;
import com.example.Pix.service.PixService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pix")
public class PixController {
    private final PixService pixService;

    public PixController(PixService pixService){
        this.pixService = pixService;

    }
    @PostMapping("/enviar")
    public TransacaoDTOResposta enviarPix(@RequestBody @Valid TransacaoDTORequisicao dtoRequisicao) {
        return pixService.processarPix(dtoRequisicao);
    }

    @GetMapping("/listar")
    public List<TransacaoDTOResposta> listar(){
       return pixService.listarTransacoes();
    }
}
