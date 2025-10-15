package com.miaudote.repository;

import com.miaudote.dto.AnimalResponseDTO;
import com.miaudote.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAnimaisByParceiroId(Long parceiroId);

}
