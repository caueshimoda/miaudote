package com.miaudote.controller;

import com.miaudote.dto.AnimalCadastroDTO;
import com.miaudote.model.Animal;
import com.miaudote.model.Usuario;
import com.miaudote.service.AnimalService;
import com.miaudote.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Animal> cadastrarAnimal(@RequestBody AnimalCadastroDTO request) {
        try{
            Animal novoAnimal = animalService.cadastrarAnimal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAnimal);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/atualizar/{animalId}/usuario/{usuarioId}")
    public ResponseEntity<Animal> atualizarAnimal(@PathVariable Long animalId, @PathVariable Long usuarioId,
                                                  @RequestBody Animal novosDados) {
        try {
            Animal animalAtualizado = animalService.atualizarAnimal(animalId, usuarioId, novosDados);
            return new ResponseEntity<>(animalAtualizado, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{animalId}/usuario/{usuarioId}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long animalId, @PathVariable Long usuarioId) {
        try {
            animalService.deletarAnimal(animalId, usuarioId);
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
