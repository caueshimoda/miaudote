package com.miaudote.repository;

import com.miaudote.model.Adotante;
import com.miaudote.model.Animal;
import com.miaudote.model.Favorito;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByAdotanteId(Long adotanteId); 
  
    Page<Favorito> findPaginasByAdotanteId(Pageable pageable, Long adotanteId); 

    List<Favorito> findByAdotanteAndAnimalIn(Adotante adotante, List<Animal> animais);

} 
