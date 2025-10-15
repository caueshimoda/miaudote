package com.miaudote.dto;
import com.miaudote.model.Animal;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
public class AnimalResponseDTO {

    private Long id;

    private ParceiroResponseDTO parceiro;

    private String especie;

    private String nome;

    private String sexo;

    private String porte;

    private String status;
    
    private int idade;

    private String obs;

    private String descricao;

    public AnimalResponseDTO(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.especie = animal.getEspecie();
        this.sexo = animal.getSexo().name();
        this.porte = animal.getPorte().name();
        this.status = animal.getStatus().name();
        this.idade = animal.getIdadeInicial() + Period.between(animal.getDataCadastro(), LocalDate.now()).getYears();
        this.obs = animal.getObs();
        this.descricao = animal.getDescricao();
        this.parceiro = new ParceiroResponseDTO(animal.getParceiro());
    }

}
