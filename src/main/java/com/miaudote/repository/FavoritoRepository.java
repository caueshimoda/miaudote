package com.miaudote.repository;

import com.miaudote.model.Favorito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
  
    List<Favorito> findByAdotanteId(Long adotanteId); 

} 
