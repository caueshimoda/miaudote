package com.miaudote.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@Table(name="Adotantes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Adotante {


    @Id
    private Long id;
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Usuario usuario;

    private String cpf;

    @Column(name = "data_nasc")
    private LocalDate dataNascimento;
}