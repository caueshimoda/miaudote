package com.miaudote.dto;

import com.miaudote.model.Animal;

import lombok.Getter;

@Getter
public class AnimalRequest {

    private long parceiroId;
    private String especie;
    private String nome;
    private Animal.Sexo sexo;
    private Animal.Porte porte;
    private Animal.Status status_ani;
    private int idade_inicial;
    private String obs;
    private String descricao;

}
