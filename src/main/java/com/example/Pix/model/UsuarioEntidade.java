package com.example.Pix.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
public class UsuarioEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String chavePix;

    private String nome;
    private String cpf;
    private String email;
    private BigDecimal saldo;







}
