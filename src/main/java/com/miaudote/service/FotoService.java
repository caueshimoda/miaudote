package com.miaudote.service;

import com.miaudote.model.Foto;
import com.miaudote.dto.FotoResponseDTO;
import com.miaudote.model.Animal;
import com.miaudote.repository.FotoRepository;

import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Foto cadastrarFoto(Long animalId, MultipartFile arquivo) throws IOException {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (fotoRepository.countByAnimalId(animalId) > 4) {
            throw new RuntimeException("Não é possível adicionar mais fotos ao animal");
        }

        Foto foto = new Foto();
        foto.setAnimal(animal);
        foto.setFoto(arquivo.getBytes());

        return fotoRepository.save(foto);

    }

    public FotoResponseDTO getFoto(Long fotoId) {
        Foto foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada com ID: " + fotoId));
        
        return new FotoResponseDTO(foto);
    }

    public List<FotoResponseDTO> getFotosPorAnimal(Long animalId) {
        List<Foto> fotos = fotoRepository.findByAnimalId(animalId);

        return fotos.stream()
               .map(FotoResponseDTO::new)
               .toList();
    }

}
