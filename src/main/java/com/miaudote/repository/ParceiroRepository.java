package com.miaudote.repository;

import com.miaudote.model.Parceiro;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {

    @EntityGraph(attributePaths = {"usuario", "usuario.cep"})
    @Override
    Optional<Parceiro> findById(Long id);

}
