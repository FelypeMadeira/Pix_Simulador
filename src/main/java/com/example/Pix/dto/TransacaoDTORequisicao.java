package com.example.Pix.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TransacaoDTORequisicao {
    @NotNull(message = "A chave é obrigatória")
    @Size(min = 1, message = "A chave de origem não pode estar vazia")
    private String chaveOrigem;

    @NotNull(message = "A chave de destino é obrigatória")
    @Size(min = 1, message = "A chave de destino não pode estar vazia")
    private String chaveDestino;

    @Positive(message = "o valor deve ser maior que zero")
    private double valor;


}
