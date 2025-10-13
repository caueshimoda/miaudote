package com.miaudote.service;

import com.miaudote.dto.AnimalRequest;
import com.miaudote.dto.AnimalResponseDTO;
import com.miaudote.dto.ParceiroResponseDTO;
import com.miaudote.model.Animal;
import com.miaudote.model.Parceiro;
import com.miaudote.repository.ParceiroRepository;
import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ParceiroRepository parceiroRepository;

    public AnimalService(AnimalRepository animalRepository, ParceiroRepository parceiroRepository) {
        this.animalRepository = animalRepository;
        this.parceiroRepository = parceiroRepository;
    }


    public Animal cadastrarAnimal(AnimalRequest request) {
        Parceiro parceiro = parceiroRepository.findById(request.getParceiroId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Animal animal = new Animal();        
        animal.setParceiro(parceiro);
        animal.setEspecie(request.getEspecie());
        animal.setNome(request.getNome());
        animal.setSexo(request.getSexo());
        animal.setPorte(request.getPorte());
        animal.setIdade_inicial(request.getIdade_inicial());
        animal.setStatus_ani(request.getStatus_ani());
        animal.setObs(request.getObs());
        animal.setDescricao(request.getDescricao());
        return animalRepository.save(animal);
    }

    public AnimalResponseDTO getAnimal(Long id, Long parceiroId) {
        Animal animal = animalRepository.findByIdAndParceiroId(id, parceiroId)
            // Lança uma exceção se o animal não for encontrado ou
            // se o animal não pertencer ao parceiroId fornecido.
            .orElseThrow(() -> new RuntimeException("Animal não encontrado ou não pertence a este parceiro."));

            return new AnimalResponseDTO(animal);
    }

    

    public List<AnimalResponseDTO> getAnimaisPorParceiro(Long parceiroId) {
        
        List<Animal> animais = animalRepository.findAnimaisByParceiroId(parceiroId);

        return animais.stream()
                .map(AnimalResponseDTO::new) 
                .toList(); 
    }

    public Animal atualizarAnimal(Long animalId, Long parceiroId, Animal novosDados) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (!animal.getParceiro().getId().equals(parceiroId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar esse animal");
        }

        Optional.ofNullable(novosDados.getNome()).ifPresent(animal::setNome);
        Optional.ofNullable(novosDados.getEspecie()).ifPresent(animal::setEspecie);
        Optional.ofNullable(novosDados.getSexo()).ifPresent(animal::setSexo);
        Optional.ofNullable(novosDados.getPorte()).ifPresent(animal::setPorte);
        Optional.ofNullable(novosDados.getStatus_ani()).ifPresent(animal::setStatus_ani);
        Optional.of(novosDados.getIdade_inicial()).ifPresent(animal::setIdade_inicial);
        Optional.ofNullable(novosDados.getDescricao()).ifPresent(animal::setDescricao);
        Optional.ofNullable(novosDados.getObs()).ifPresent(animal::setObs);

        return animalRepository.save(animal);
    }

    public void deletarAnimal(Long animalId, Long parceiroId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (!animal.getParceiro().getId().equals(parceiroId)) {
            throw new RuntimeException("Usuário não autorizado a excluir esse animal");
        }

        animalRepository.delete(animal);
    }




    // USADOS PARA TESTES -------------------------------------------------------------

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
        /* 
        if (!(novosDados.getParceiro() instanceof Parceiro)) {
            throw new IllegalArgumentException("Somente parceiros podem cadastrar animais");
        }*/
        return animalRepository.findById(id)
            .map(animalExistente -> {
                animalExistente.setEspecie(novosDados.getEspecie());
                animalExistente.setNome(novosDados.getNome());
                animalExistente.setSexo(novosDados.getSexo());
                animalExistente.setPorte(novosDados.getPorte());
                animalExistente.setParceiro(novosDados.getParceiro());
                animalExistente.setStatus_ani(novosDados.getStatus_ani());
                animalExistente.setIdade_inicial(novosDados.getIdade_inicial());
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
