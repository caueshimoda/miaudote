package com.miaudote.repository;

import com.miaudote.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepo extends JpaRepository<Base, Long> {
    /*
    JpaRepository é uma interface que tem vários métodos prontos que vão ajudar a gente a add no BD, consultar, deletar, etc
    Sintaxe: JpaRepository<[NOME_CLASSE],[TIPO_ID]>
     */
}
