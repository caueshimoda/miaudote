package com.miaudote.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="favoritos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_adotante", nullable = false)
    private Adotante adotante;

    @ManyToOne
    @JoinColumn(name = "id_animal", nullable = false)
    private Animal animal;

    @CreationTimestamp
    @Column(name="data_cad", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

}
