package com.miaudote.service;

import com.miaudote.model.Animal;
import com.miaudote.model.Parceiro;
import com.miaudote.model.Usuario;
import com.miaudote.repository.UsuarioRepository;
import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final UsuarioRepository usuarioRepository;

    public AnimalService(AnimalRepository animalRepository, UsuarioRepository usuarioRepository) {
        this.animalRepository = animalRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public Animal cadastrarAnimal(Long usuarioId, Animal animal) {
        Usuario parceiro = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        animal.setParceiro(parceiro);
        return animalRepository.save(animal);
    }

    public Animal atualizarAnimal(Long animalId, Long usuarioId, Animal novosDados) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (!animal.getParceiro().getId().equals(usuarioId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar esse animal");
        }

        Optional.ofNullable(novosDados.getNome()).ifPresent(animal::setNome);
        Optional.ofNullable(novosDados.getEspecie()).ifPresent(animal::setEspecie);
        Optional.ofNullable(novosDados.getSexo()).ifPresent(animal::setSexo);
        Optional.ofNullable(novosDados.getPorte()).ifPresent(animal::setPorte);
        Optional.of(novosDados.getIdade_inicial()).ifPresent(animal::setIdade_inicial);
        Optional.ofNullable(novosDados.getObs()).ifPresent(animal::setObs);

        return animalRepository.save(animal);
    }

    public void deletarAnimal(Long animalId, Long usuarioId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (!animal.getParceiro().getId().equals(usuarioId)) {
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
        if (!(novosDados.getParceiro() instanceof Parceiro)) {
            throw new IllegalArgumentException("Somente parceiros podem cadastrar animais");
        }
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
