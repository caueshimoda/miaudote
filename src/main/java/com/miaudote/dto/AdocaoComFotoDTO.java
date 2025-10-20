package com.miaudote.dto;

import java.util.List;
import java.util.Objects;

import com.miaudote.model.AdocaoSolicitada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdocaoComFotoDTO {

    private List<FotoResponseDTO> fotos;
    private AdocaoSolicitadaResponseDTO adocao;

    public AdocaoComFotoDTO(AdocaoSolicitada adocaoSolicitada, List<FotoResponseDTO> fotos) {
        Objects.requireNonNull(adocaoSolicitada, "A entidade Adoção Solicitada não pode ser nula ao criar AdocaoComFotoDTO.");

        this.adocao = new AdocaoSolicitadaResponseDTO(adocaoSolicitada);
        this.fotos = fotos;
    }

}

