package com.miaudote.dto;

import com.miaudote.model.Foto;
import lombok.Getter;

@Getter
public class FotoResponseDTO {

    private Long id;
    private Long animalId;
    private byte[] foto;

    public FotoResponseDTO(Foto foto) {
        this.id = foto.getId();
        this.animalId = foto.getAnimal().getId();
        this.foto = foto.getFoto();
    }

}
