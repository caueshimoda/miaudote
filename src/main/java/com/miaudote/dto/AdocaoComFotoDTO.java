package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.AdocaoSolicitada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdocaoComFotoDTO {

    private FotoResponseDTO foto;
    private AdocaoSolicitadaResponseDTO adocao;

    public AdocaoComFotoDTO(AdocaoSolicitada adocaoSolicitada, FotoResponseDTO foto) {
        Objects.requireNonNull(adocaoSolicitada, "A entidade Adoção Solicitada não pode ser nula ao criar AdocaoSolicitadalResponseDTO.");
        Objects.requireNonNull(foto, "A entidade Foto não pode ser nula ao criar AdocaoSolicitadalResponseDTO.");

        this.adocao = new AdocaoSolicitadaResponseDTO(adocaoSolicitada);
        this.foto = foto;
    }

}

