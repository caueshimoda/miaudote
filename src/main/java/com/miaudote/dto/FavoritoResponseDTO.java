package com.miaudote.dto;

import com.miaudote.model.Favorito;
import lombok.Getter;

@Getter
public class FavoritoResponseDTO {

    private Long id; 
    private AdotanteResponseDTO adotante;
    private AnimalResponseDTO animal;

    public FavoritoResponseDTO(Favorito favorito) {
        this.id = favorito.getId();
        this.adotante = new AdotanteResponseDTO(favorito.getAdotante());
        this.animal = new AnimalResponseDTO(favorito.getAnimal());
    }

}
