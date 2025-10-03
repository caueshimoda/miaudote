package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="Parceiros")
//@DiscriminatorValue("Parceiros")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parceiro {


    @Id
    private Long id;
    @OneToOne
    @MapsId   // <-- garante que o ID vem de Usuario
    @JoinColumn(name = "id") // FK para usuarios.id
    private Usuario usuario;

    private String documento;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public enum Tipo {
        ONG,
        Protetor,
    }

    private String site;
}
