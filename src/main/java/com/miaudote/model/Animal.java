package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(name="data_cad", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_ani")
    private Status status;

    public enum Status {
        Adotado,
        Disponível,
        Indisponível
    }
    
    @Column(name = "idade_inicial")
    private int idadeInicial;

    private String obs;

    private String descricao;

    public boolean isValidAnimal() {
        return isValidEspecie() && isValidNome() && isValidIdade();
    }

    public boolean isValidEspecie() {
        return Validacao.isValidNome(especie);
    }

    public boolean isValidNome(){
        return Validacao.isValidNome(nome);
    }

    public boolean isValidIdade() {
        return getIdadeInicial() >= 0;
    }

    public boolean isValidDescricao(){
        return Validacao.isValidText(descricao);
    }

    public boolean isValidObs(){
        return Validacao.isValidText(obs);
    }

    public boolean isDisponivel() {
        return status.equals(Status.Disponível);
    }

}
