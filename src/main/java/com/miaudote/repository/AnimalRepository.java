package com.miaudote.repository;

import com.miaudote.model.Animal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAnimaisByParceiroId(Long parceiroId);

    // Pra devolver animais por página
    @Override
    Page<Animal> findAll(Pageable pageable);

}
