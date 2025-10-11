package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.time.LocalDate;
//import java.util.List;

@Entity
@Table(name="ceps")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String logradouro;
    private String cidade;
    private String estado;
    private String bairro;

}
