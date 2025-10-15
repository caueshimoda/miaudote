package com.miaudote.repository;

import com.miaudote.model.AdocaoSolicitada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdocaoSolicitadaRepository extends JpaRepository<AdocaoSolicitada, Long> {

    List<AdocaoSolicitada> findByAdotanteId(Long adotanteId); 

    List<AdocaoSolicitada> findByAnimalParceiroId(Long parceiroId);

}