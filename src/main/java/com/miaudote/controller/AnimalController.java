package com.miaudote.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<?> cadastrarAnimal(@ModelAttribute AnimalRequest request) {
        if (request.getFotos() == null || request.getFotos().isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma foto é obrigatória para o cadastro do animal.");
        }
        
        try {
            AnimalResponseDTO novoAnimal = animalService.cadastrarAnimal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAnimal);

        } catch (IOException e) {
                return ResponseEntity.badRequest().body("Erro ao processar arquivo(s)" + e.getMessage());

        } catch (RuntimeException e) {
                e.printStackTrace(); 
                return ResponseEntity.badRequest().body("Erro ao cadastrar animal: " + e.getMessage());
        }
    }

    /* 
    Sei que o DTO parece inútil, e pro nosso caso talvez seja, mas sem ele a gente mandaria os dados do parceiro também,
    e no futuro poderia dar problema de acoplamento (se precisar mudar algo na entidade Animal, 
    adicionar um campo por exemplo, isso pode afetar diretamente o contrato da API e quebrar clientes)
    Quebrar cliente é coisa de agiota, o que é meio badass, mas a gente não tá nesse nível de SIGMA :/
    */

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimal(@PathVariable Long id) {
        try {
            AnimalResponseDTO animal = animalService.getAnimal(id);
            return ResponseEntity.ok(animal);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar animal: " + e.getMessage());
        }
    }

    /* Acho que não vamos precisar disso, mas se quiser voltar tem que descomentar o código em animalService também!

    @GetMapping("/all")
    public ResponseEntity<?> getAnimais() {
        try {
            List<AnimalResponseDTO> animais = animalService.getAnimais();
            return ResponseEntity.ok(animais);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar animais: " + e.getMessage());
        }
    }*/

    @GetMapping("/parceiro/{parceiroId}")
    public ResponseEntity<?> getAnimaisPorParceiro(@PathVariable Long parceiroId) {
        try {
            List<AnimalResponseDTO> animais = animalService.getAnimaisPorParceiro(parceiroId);
            return ResponseEntity.ok(animais);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar animais: " + e.getMessage());
        }
    }

    @PatchMapping("/{animalId}")
    public ResponseEntity<?> atualizarAnimal(@PathVariable Long animalId,
                                             @RequestBody Animal novosDados) {
        try {
            Animal animalAtualizado = animalService.atualizarAnimal(animalId, novosDados);
            return new ResponseEntity<>(new AnimalResponseDTO(animalAtualizado), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao atualizar animal: " + e.getMessage()); 
        }
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long animalId) {
        try {
            animalService.deletarAnimal(animalId);
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
