package com.example.Pix.dto;

import com.example.Pix.model.TransacaoEntidade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransacaoDTOResposta {

    private UUID id;
    private String chaveOrigem;
    private String chaveDestino;
    private double valor;
    private LocalDateTime dataHora;
    private String status;

    public TransacaoDTOResposta (TransacaoEntidade entidade){
        this.id = entidade.getId();
        this.chaveOrigem = entidade.getChaveOrigem();
        this.chaveDestino = entidade.getChaveDestino();
        this.valor = entidade.getValor();
        this.dataHora = entidade.getDataHora();
        this.status = entidade.getStatus();
    }
}
