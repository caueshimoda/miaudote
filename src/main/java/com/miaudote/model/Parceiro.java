package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Parceiros")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parceiro extends Usuario {

    /* 
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;*/

    private int documento;
}
