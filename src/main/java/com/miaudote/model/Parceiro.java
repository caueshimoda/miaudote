package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="Parceiros")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parceiro extends Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
