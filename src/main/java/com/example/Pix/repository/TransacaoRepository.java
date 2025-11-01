package com.example.Pix.repository;

import com.example.Pix.model.TransacaoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntidade, UUID> {
}
