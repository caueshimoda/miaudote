package com.miaudote.service;

import com.miaudote.dto.FotoCadastroDTO;
import com.miaudote.model.Foto;
import com.miaudote.model.Animal;
import com.miaudote.repository.FotoRepository;
import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import java.io.IOException;

@Service
public class FotoService {

    private final FotoRepository fotoRepository;
    private final AnimalRepository animalRepository;

    public FotoService(FotoRepository fotoRepository, AnimalRepository animalRepository) {
        this.fotoRepository = fotoRepository;
        this.animalRepository = animalRepository;
    }


    public Foto cadastrarFoto(FotoCadastroDTO request) throws IOException {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        Foto foto = new Foto();
        foto.setAnimal(animal);
        foto.setFoto(request.getArquivo().getBytes());

        return fotoRepository.save(foto);
    }

}
