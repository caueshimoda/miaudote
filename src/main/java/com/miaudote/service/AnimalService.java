package com.miaudote.service;

import com.miaudote.model.Animal;
import com.miaudote.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> listAnimais() {
        return animalRepository.findAll();
    }

    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal não encontrado com id: " + id));
    }

    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Long id, Animal novosDados) {
        return animalRepository.findById(id)
            .map(animalExistente -> {
                animalExistente.setEspecie(novosDados.getEspecie());
                animalExistente.setNome(novosDados.getNome());
                animalExistente.setSexo(novosDados.getSexo());
                animalExistente.setPorte(novosDados.getPorte());
                animalExistente.setParceiro(novosDados.getParceiro());
                animalExistente.setStatus(novosDados.getStatus());
                animalExistente.setDataCad(novosDados.getDataCad());
                animalExistente.setIdadeInicial(novosDados.getIdadeInicial());
                animalExistente.setObs(novosDados.getObs());
                animalExistente.setDescricao(novosDados.getDescricao());
                return animalRepository.save(animalExistente);
            })
            .orElseThrow(() -> new RuntimeException("Animal não encontrado com id: " + id));
    }

    public void deleteAnimal(Long id) {
    Animal animal = animalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Animal não encontrado com id: " + id));
    animalRepository.delete(animal);
}

}
