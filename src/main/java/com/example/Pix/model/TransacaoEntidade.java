package com.example.Pix.model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;



@Getter
@Setter
@Entity
public class TransacaoEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String chaveOrigem;
    private String chaveDestino;
    private BigDecimal valor;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataHora ;

    private String status;// PROCESSANDO, APROVADO, FALHA


    //Getters and Setters


}
