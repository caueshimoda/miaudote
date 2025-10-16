package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.Favorito;
import lombok.Getter;

@Getter
public class FavoritoResponseDTO {

    private Long id; 
    private AdotanteResponseDTO adotante;
    private AnimalResponseDTO animal;

    public FavoritoResponseDTO(Favorito favorito) {
        Objects.requireNonNull(favorito, "A entidade Favorito não pode ser nula ao criar FavoritoResponseDTO.");

        this.id = favorito.getId();
        this.adotante = new AdotanteResponseDTO(favorito.getAdotante());
        this.animal = new AnimalResponseDTO(favorito.getAnimal());
    }

}
