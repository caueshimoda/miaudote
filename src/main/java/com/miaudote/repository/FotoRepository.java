package com.miaudote.repository;

import com.miaudote.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    int countByAnimalId(Long animalId);

    List<Foto> findByAnimalId(Long animalId);

    Optional<Foto> findFirstByAnimalIdOrderByIdAsc(Long animalId);

    // Essa query é pra ele pegar todos os dados dos animais da página em uma só consulta, melhorando a performance
    @Query("SELECT f FROM Foto f " +
           "WHERE f.id IN (" +
                "SELECT MIN(f2.id) FROM Foto f2 " +
                "WHERE f2.animal.id IN :animalIds " +
                "GROUP BY f2.animal.id" +
           ")")
    List<Foto> findPrimeirasFotosByAnimalIdIn(@Param("animalIds") List<Long> animalIds);
  
} 
