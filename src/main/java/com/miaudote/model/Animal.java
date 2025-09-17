package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Animais")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_parceiro", nullable = false)
    private Usuario parceiro;

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
    private Status status;

    public enum Status {
        Adotado,
        Disponível,
        Indisponível
    }
    
    private int idadeInicial;

    private String obs;

    private String descricao;

    private LocalDate dataCad;

    public boolean isValidEspecie() {
        try {
            String especie = getEspecie();
            if (especie == null || especie.trim().isEmpty())
                return false;

            if (especie.trim().length() > 20)
                return false;

            // Apenas letras e espaços
            return especie.matches("^[\\p{L} ]+$");

        } catch (Exception e){
            return false;
        }
    }

    public boolean isValidNome(){
        try {
            String nome = getNome();
            if (nome == null || nome.trim().isEmpty())
                return false;

            if (nome.trim().length() < 2 || nome.trim().length() > 60)
                return false;

            // Apenas letras e espaços
            return nome.matches("^[\\p{L} ]+$");

        } catch (Exception e){
            return false;
        }
    }

    public boolean isValidIdade() {
        return getIdadeInicial() >= 0;
    }
}
