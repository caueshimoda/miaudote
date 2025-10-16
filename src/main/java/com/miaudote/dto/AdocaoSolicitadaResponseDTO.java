package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.AdocaoSolicitada;
import lombok.Getter;

@Getter
public class AdocaoSolicitadaResponseDTO {

    private Long id; 
    private AdotanteResponseDTO adotante;
    private AnimalResponseDTO animal;
    private String status;

    public AdocaoSolicitadaResponseDTO(AdocaoSolicitada adocaoSolicitada) {
        Objects.requireNonNull(adocaoSolicitada, "A entidade Adoção Solicitada não pode ser nula ao criar AdocaoSolicitadalResponseDTO.");

        this.id = adocaoSolicitada.getId();
        this.adotante = new AdotanteResponseDTO(adocaoSolicitada.getAdotante());
        this.animal = new AnimalResponseDTO(adocaoSolicitada.getAnimal());

        if (adocaoSolicitada.getStatus() == null) {
            this.status = "Status Indefinido"; 
        } else if (adocaoSolicitada.getStatus().equals("Em andamento") || adocaoSolicitada.getStatus().equals("Na fila")) {
            this.status = "Solitação Aberta"; 
        } else if (adocaoSolicitada.getStatus().substring(0, "Finalizada".length()).equals("Finalizada")) {
            this.status = "Encerrada";
        }
        else {
            this.status = "Status da solicitação: desconhecido";
        }
    }

}
