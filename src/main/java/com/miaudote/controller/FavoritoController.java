package com.miaudote.controller;

import com.miaudote.dto.FavoritoResponseDTO;
import com.miaudote.dto.FavoritoRequest;
import com.miaudote.service.FavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarFavorito(@RequestBody FavoritoRequest request){
        try {
            FavoritoResponseDTO favorito = favoritoService.cadastrarFavorito(request);
            return new ResponseEntity<>(favorito, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao cadastrar favorito: " + e.getMessage());
        }
    }

     @GetMapping("/{id}")
    public ResponseEntity<?> getFavoritoById(@PathVariable Long id) {
        FavoritoResponseDTO favoritoDTO = favoritoService.getFavorito(id);
        
        try {
            return ResponseEntity.ok(favoritoDTO);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar favorito: " + e.getMessage());
        }
    }

    @GetMapping("/adotante/{id}")
    public ResponseEntity<?> getFavoritosByAdotanteId(@PathVariable Long id) {
        List<FavoritoResponseDTO> favoritos = favoritoService.getFavoritosPorAdotante(id);

        try {
            return ResponseEntity.ok(favoritos);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar favoritos: " + e.getMessage());
        }
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarFavorito(@PathVariable Long id){
        try{
            favoritoService.deletarFavorito(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
