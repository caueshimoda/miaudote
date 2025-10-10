package com.miaudote.repository;

import com.miaudote.model.AdocaoSolicitada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdocaoSolicitadaRepository extends JpaRepository<AdocaoSolicitada, Long> {

}