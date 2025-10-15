package com.miaudote.repository;

import com.miaudote.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    int countByAnimalId(Long animalId);

    List<Foto> findByAnimalId(Long animalId);
  
} 
