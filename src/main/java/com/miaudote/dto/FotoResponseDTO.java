package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.Animal;
import com.miaudote.model.Foto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FotoResponseDTO {

    private Long id;
    private AnimalResponseDTO animal;
    private byte[] foto;

    // Construtor para mandar apenas dados da foto
    public FotoResponseDTO(Foto foto) {
        Objects.requireNonNull(foto, "A entidade Foto não pode ser nula ao criar FotoResponseDTO.");

        this.id = foto.getId();
        this.foto = foto.getFoto();
        this.animal = null;
    }

    // Construtor caso seja necessário mandar os dados do animal também. 
    // OBS: daria pra dar um getAnimal() na foto, porém minha intenção é justamente que haja um Overload, 
    // e quem chamar esse método deve deixar explícito que quer os dados do animal.
    public FotoResponseDTO(Foto foto, Animal animal) {
        Objects.requireNonNull(foto, "A entidade Foto não pode ser nula ao criar FotoResponseDTO.");
        Objects.requireNonNull(animal, "A entidade Animal não pode ser nula ao criar FotoResponseDTO.");

        this.id = foto.getId();
        this.foto = foto.getFoto();
        this.animal = new AnimalResponseDTO(animal);
    }

}
