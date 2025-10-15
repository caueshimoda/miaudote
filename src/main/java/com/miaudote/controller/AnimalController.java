package com.miaudote.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miaudote.dto.AnimalRequest;
import com.miaudote.dto.AnimalResponseDTO;
import com.miaudote.model.Animal;
import com.miaudote.service.AnimalService;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<AnimalResponseDTO> cadastrarAnimal(@RequestBody AnimalRequest request) {
        if (request.getFotos() == null || request.getFotos().isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma foto é obrigatória para o cadastro do animal.");
        }
        
        try {
            AnimalResponseDTO novoAnimal = animalService.cadastrarAnimal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAnimal);

        } catch (IOException e) {
                System.out.println("Erro ao processar arquivo(s)");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /* 
    Sei que o DTO parece inútil, e pro nosso caso talvez seja, mas sem ele a gente mandaria os dados do parceiro também,
    e no futuro poderia dar problema de acoplamento (se precisar mudar algo na entidade Animal, 
    adicionar um campo por exemplo, isso pode afetar diretamente o contrato da API e quebrar clientes)
    Quebrar cliente é coisa de agiota, o que é meio badass, mas a gente não tá nesse nível de SIGMA :/
    */

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> getAnimal(@PathVariable Long id) {
        try {
            AnimalResponseDTO animal = animalService.getAnimal(id);
            return ResponseEntity.ok(animal);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/parceiro/{parceiroId}")
    public ResponseEntity<List<AnimalResponseDTO>> getAnimaisPorParceiro(@PathVariable Long parceiroId) {
        try {
            List<AnimalResponseDTO> animais = animalService.getAnimaisPorParceiro(parceiroId);
            return ResponseEntity.ok(animais);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnimalResponseDTO>> getAnimais() {
        try {
            List<AnimalResponseDTO> animais = animalService.getAnimais();
            return ResponseEntity.ok(animais);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{animalId}/parceiro/{parceiroId}")
    public ResponseEntity<Animal> atualizarAnimal(@PathVariable Long animalId, @PathVariable Long parceiroId,
                                                  @RequestBody Animal novosDados) {
        try {
            Animal animalAtualizado = animalService.atualizarAnimal(animalId, parceiroId, novosDados);
            return new ResponseEntity<>(animalAtualizado, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{animalId}/parceiro/{parceiroId}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long animalId, @PathVariable Long parceiroId) {
        try {
            animalService.deletarAnimal(animalId, parceiroId);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    // USADOS PARA TESTE ----------------------------------------------------
    /*

    @PostMapping("/cadastrar")
    public ResponseEntity<Animal> cadastrarAnimal(@RequestBody Animal animal){
        try {
            Animal salvo = animalService.addAnimal(animal);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    */
}
