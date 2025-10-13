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

    // Anotação que diz ao Hibernate para buscar os objetos Parceiro e Usuario junto com os Animais em uma única query.
    // Isso é necessário porque o hibernate tava fazendo em duas queries diferentes, mas o banco de dados era fechado
    // antes da segunda query e isso causava um bad request.
    @EntityGraph(attributePaths = {"parceiro", "parceiro.usuario"})
    // Isso vai servir pro Spring fazer a query com o where id_parceiro = parceiroId
    List<Animal> findAnimaisByParceiroId(Long parceiroId);

    @EntityGraph(attributePaths = {"parceiro", "parceiro.usuario"})
    Optional<Animal> findByIdAndParceiroId(Long animalId, Long parceiroId); 

}
