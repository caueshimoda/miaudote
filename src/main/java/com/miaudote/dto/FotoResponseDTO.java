package com.miaudote.dto;

import com.miaudote.model.Foto;
import lombok.Getter;

@Getter
public class FotoResponseDTO {

    private Long id;
    private AnimalResponseDTO animal;
    private byte[] foto;

    public FotoResponseDTO(Foto foto) {
        this.id = foto.getId();
        this.foto = foto.getFoto();
        this.animal = new AnimalResponseDTO(foto.getAnimal());
    }

}
