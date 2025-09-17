package com.miaudote.controller;

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
    public ResponseEntity<Animal> cadastrarAnimal(@RequestBody Animal animal){
        try {
            Animal salvo = animalService.addAnimal(animal);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
