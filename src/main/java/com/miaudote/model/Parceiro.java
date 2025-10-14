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
        return isValidDocumento();
    }

    public boolean isValidDocumento() {
        if (tipo.name() == null) 
            return false;

        // 14 dígitos para CNPJ
        if (tipo.name() == "ONG") 
            return documento.matches("^\\d{14}$");

        // 11 dígitos para CPF
        if (tipo.name() == "Protetor") 
            return documento.matches("^\\d{11}$");

        return false;
    }

}
