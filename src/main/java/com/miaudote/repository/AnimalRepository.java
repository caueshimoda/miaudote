package com.miaudote.repository;

import com.miaudote.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAnimaisByParceiroId(Long parceiroId);

    List<Animal> findByIdBetweenOrderByIdAsc(Long startId, Long endId);

}
