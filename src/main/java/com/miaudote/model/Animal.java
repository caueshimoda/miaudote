package com.miaudote.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name="Animais")
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY);
    private long id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Parceiro parceiro;

    private String especie;

    private String nome;

    private String sexo;

    private String porte;

    private String status;
    
    private int idadeInicial;

    private String obs;

    private String descricao;

    private LocalDate dataCad;
}
