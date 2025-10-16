package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Foto;

import lombok.Getter;

@Getter
public class AdocaoComFotoDTO {

    private Long id; 
    private AdotanteResponseDTO adotante;
    private FotoResponseDTO foto;
    private String status;

    public AdocaoComFotoDTO(AdocaoSolicitada adocaoSolicitada, FotoResponseDTO foto) {
        Objects.requireNonNull(adocaoSolicitada, "A entidade Adoção Solicitada não pode ser nula ao criar AdocaoSolicitadalResponseDTO.");

        this.id = adocaoSolicitada.getId();
        this.adotante = new AdotanteResponseDTO(adocaoSolicitada.getAdotante());
        this.foto = foto;

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

