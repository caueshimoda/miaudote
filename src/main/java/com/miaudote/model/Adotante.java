package com.miaudote.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    public boolean isValidAdotante() {
        return isValidCpf() && isValidDataNascimento();
    }

    public boolean isValidDataNascimento() {

        return Validacao.isValidData(dataNascimento);
        
    }

    public boolean isValidCpf() {
        
        return Validacao.isValidCPF(cpf);

    }

}