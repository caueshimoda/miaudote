package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.time.LocalDate;
//import java.util.List;

@Entity
@Table(name="Animais")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_parceiro", nullable = false)
    private Parceiro parceiro;

    private String especie;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public enum Sexo {
        Macho,
        Fêmea
    }

    @Enumerated(EnumType.STRING)
    private Porte porte;

    public enum Porte {
        Pequeno,
        Médio,
        Grande
    }

    @Enumerated(EnumType.STRING)
    private Status status_ani;

    public enum Status {
        Adotado,
        Disponível,
        Indisponível
    }
    
    private int idade_inicial;

    private String obs;

    private String descricao;

    public boolean isValidAnimal() {
        return isValidEspecie() && isValidNome() && isValidIdade();
    }

    public boolean isValidEspecie() {
        return ValidacaoNome.isValidNome(especie);
    }

    public boolean isValidNome(){
        return ValidacaoNome.isValidNome(nome);
    }

    public boolean isValidIdade() {
        return getIdade_inicial() >= 0;

    }
}
