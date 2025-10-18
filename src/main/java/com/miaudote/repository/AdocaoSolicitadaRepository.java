package com.miaudote.repository;

import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Adotante;
import com.miaudote.model.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdocaoSolicitadaRepository extends JpaRepository<AdocaoSolicitada, Long> {

    List<AdocaoSolicitada> findByAdotanteId(Long adotanteId); 

    List<AdocaoSolicitada> findByAnimalParceiroId(Long parceiroId);

    boolean existsByAdotanteAndAnimalAndStatus(Adotante adotante, Animal animal, String status);

}