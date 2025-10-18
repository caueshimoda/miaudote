package com.miaudote.service;

import com.miaudote.dto.AnimalRequest;
import com.miaudote.dto.AnimalResponseDTO;
import com.miaudote.model.Animal;
import com.miaudote.model.Parceiro;
import com.miaudote.repository.ParceiroRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.FotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ParceiroRepository parceiroRepository;
    private final FotoService fotoService;

    public AnimalService(AnimalRepository animalRepository, ParceiroRepository parceiroRepository, 
                        FotoRepository fotoRepository, FotoService fotoService) {
        this.animalRepository = animalRepository;
        this.parceiroRepository = parceiroRepository;
        this.fotoService = fotoService;
    }

    @Transactional
    public AnimalResponseDTO cadastrarAnimal(AnimalRequest request) throws IOException {
        Parceiro parceiro = parceiroRepository.findById(request.getParceiroId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Animal animal = new Animal();        
        animal.setParceiro(parceiro);
        animal.setEspecie(request.getEspecie());
        animal.setNome(request.getNome());
        animal.setSexo(request.getSexo());
        animal.setPorte(request.getPorte());
        animal.setIdadeInicial(request.getIdadeInicial());
        animal.setStatus(request.getStatus());
        animal.setObs(request.getObs());
        animal.setDescricao(request.getDescricao());

        if (!animal.isValidEspecie())
            throw new IllegalArgumentException("Espécie do Animal inválida, deve ter apenas letras e espaços, entre 2 e 60 caracteres.");

        if (!animal.isValidNome())
            throw new IllegalArgumentException("Nome do Animal inválido, deve ter apenas letras e espaços, entre 2 e 60 caracteres.");

        if (!animal.isValidIdade())
            throw new IllegalArgumentException("Idade do Animal inválida, não pode ser negativa.");
        
        Animal animalSalvo = animalRepository.save(animal);

        fotoService.cadastrarFotos(animalSalvo.getId(), request.getFotos());

        return new AnimalResponseDTO(animalSalvo);
        
    }

    public AnimalResponseDTO getAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Animal não encontrado."));

            return new AnimalResponseDTO(animal);
    }

    /* Acho que não vamos precisar disso
    public List<AnimalResponseDTO> getAnimais() {
        
        List<Animal> animais = animalRepository.findAll();

        return animais.stream()
                .map(AnimalResponseDTO::new) 
                .toList(); 
    }
    */

    public List<AnimalResponseDTO> getAnimaisPorParceiro(Long parceiroId) {
        
        List<Animal> animais = animalRepository.findByParceiroId(parceiroId);

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
        Optional.ofNullable(novosDados.getStatus()).ifPresent(animal::setStatus);
        Optional.ofNullable(novosDados.getIdadeInicial()).ifPresent(animal::setIdadeInicial);
        Optional.ofNullable(novosDados.getDescricao()).ifPresent(animal::setDescricao);
        Optional.ofNullable(novosDados.getObs()).ifPresent(animal::setObs);

        if (!animal.isValidEspecie())
            throw new IllegalArgumentException("Espécie do Animal inválida, deve ter apenas letras e espaços, entre 2 e 60 caracteres.");

        if (!animal.isValidNome())
            throw new IllegalArgumentException("Nome do Animal inválido, deve ter apenas letras e espaços, entre 2 e 60 caracteres.");

        if (!animal.isValidIdade())
            throw new IllegalArgumentException("Idade do Animal inválida, não pode ser negativa.");

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
                animalExistente.setStatus(novosDados.getStatus());
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
