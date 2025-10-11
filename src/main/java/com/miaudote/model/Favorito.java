package com.miaudote.model;

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

}
