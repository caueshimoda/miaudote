package com.miaudote.repository;

import com.miaudote.model.Adotante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdotanteRepository extends JpaRepository<Adotante, Long> {

    boolean existsByCpf(String cpf);

    boolean existsById(Long id);

}
