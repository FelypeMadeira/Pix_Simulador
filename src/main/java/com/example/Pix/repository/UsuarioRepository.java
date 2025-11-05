package com.example.Pix.repository;

import com.example.Pix.model.UsuarioEntidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntidade, UUID> {

    Optional<UsuarioEntidade> findByChavePix(String chavePix);
}
