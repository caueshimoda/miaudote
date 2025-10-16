package com.miaudote.dto;

import com.miaudote.model.Animal;
import com.miaudote.model.Foto;
import lombok.Getter;

@Getter
public class FotoResponseDTO {

    private Long id;
    private AnimalResponseDTO animal;
    private byte[] foto;

    // Construtor para mandar apenas dados da foto
    public FotoResponseDTO(Foto foto) {
        this.id = foto.getId();
        this.foto = foto.getFoto();
        this.animal = null;
    }

    // Construtor caso seja necessário mandar os dados do animal também. 
    // OBS: daria pra dar um getAnimal() na foto, porém minha intenção é justamente 
    // que haja um Overload pra quem chamar esse método deixar explícito que quer os dados do animal.
    public FotoResponseDTO(Foto foto, Animal animal) {
        this.id = foto.getId();
        this.foto = foto.getFoto();
        this.animal = new AnimalResponseDTO(animal);
    }

}
