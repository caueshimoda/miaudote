package com.miaudote.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.miaudote.model.Animal;

import lombok.Getter;

@Getter
public class AnimalRequest {

    private long parceiroId;
    private String especie;
    private String nome;
    private Animal.Sexo sexo;
    private Animal.Porte porte;
    private Animal.Status status;
    private int idadeInicial;
    private String obs;
    private String descricao;
    private List<MultipartFile> fotos; 

}
