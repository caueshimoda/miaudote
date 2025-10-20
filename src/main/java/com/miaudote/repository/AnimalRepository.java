package com.miaudote.repository;

import com.miaudote.model.Animal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Page<Animal> findPaginasByParceiroId(Pageable pageable, Long parceiroId);

    List<Animal> findByParceiroId(Long parceiroId);

    @Override
    Page<Animal> findAll(Pageable pageable);


}
