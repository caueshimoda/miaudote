package com.miaudote.service;

import com.miaudote.model.Foto;
import com.miaudote.dto.FotoResponseDTO;
import com.miaudote.model.Animal;
import com.miaudote.model.Foto;
import com.miaudote.repository.FotoRepository;

import jakarta.transaction.Transactional;

import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    // Como é uma lista de fotos, precisa ter o @Transactional pra ele desfazer o cadastro caso haja erro em alguma foto 
    @Transactional
    public List<Foto> cadastrarFotos(Long animalId, List<MultipartFile> arquivos) throws IOException {

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (fotoRepository.countByAnimalId(animalId) + arquivos.size() > 5) {
            throw new RuntimeException("Não é possível adicionar essa quantidade de fotos ao animal");
        }

        return arquivos.stream().map(arquivo -> {
                try {
                    Foto foto = new Foto();
                    foto.setAnimal(animal);
                    foto.setFoto(arquivo.getBytes()); 

                    return fotoRepository.save(foto);
                } catch (IOException e) {
                    
                    throw new RuntimeException("Falha ao processar o arquivo: " + arquivo.getOriginalFilename(), e);
                }
            }).toList();

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

    public FotoResponseDTO getPrimeiraFotoDoAnimal(Long animalId) {
        Foto foto = fotoRepository.findFirstByAnimalIdOrderByIdAsc(animalId)
                .orElseThrow(() -> new RuntimeException("Nenhuma foto encontrada para o animal ID: " + animalId));

        return new FotoResponseDTO(foto);
    }

    public List<FotoResponseDTO> getFotosPorParceiro(Long parceiroId) {
        List<Animal> animais = animalRepository.findAnimaisByParceiroId(parceiroId);

        List<FotoResponseDTO> fotos = new ArrayList<>();

        for (Animal animal : animais) {
            fotos.add(getPrimeiraFotoDoAnimal(animal.getId()));
        }

        return fotos;
    }

    public void deletarFoto(Long id, Long animalId){
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        if (fotoRepository.countByAnimalId(animalId) < 2) {
            throw new RuntimeException("Não é possível deletar a foto, o animal precisa ter pelo menos uma");
        }

        // Acho que pra essa classe não precisaria disso, mas deixei por precaução
        try {
            fotoRepository.delete(foto);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: foto vinculado a outros registros", e);
        }
    }

}
