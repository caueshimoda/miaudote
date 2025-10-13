package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Parceiros")
//@DiscriminatorValue("Parceiros")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parceiro {


    @Id
    private Long id;
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Usuario usuario;

    private String documento;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public enum Tipo {
        ONG,
        Protetor,
    }

    private String site;

    public boolean isValidParceiro() {
        return true;
    }
}
