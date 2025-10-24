package com.miaudote.controller;

import com.miaudote.model.Adotante;
import com.miaudote.dto.AdotanteCadastroDTO;
import com.miaudote.dto.AdotanteResponseDTO;
import com.miaudote.service.AdotanteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adotantes")
public class AdotanteController {
  private final AdotanteService adotanteService;

    public AdotanteController(AdotanteService adotanteService) {
        this.adotanteService = adotanteService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarAdotante(@RequestBody AdotanteCadastroDTO request){
        try {
            Adotante adotante = adotanteService.cadastrarAdotante(request);
            return new ResponseEntity<>(new AdotanteResponseDTO(adotante), HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }  

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdotanteById(@PathVariable Long id) {
        try {
            AdotanteResponseDTO adotanteDTO = adotanteService.getAdotante(id);
            return ResponseEntity.ok(adotanteDTO);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar adotante: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarAdotante(@PathVariable Long id, @RequestBody AdotanteCadastroDTO adotante){
        try {

            Adotante adotanteAtualizado = adotanteService.atualizarAdotante(id, adotante);

            return new ResponseEntity<>(new AdotanteResponseDTO(adotanteAtualizado), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao atualizar adotante: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAdotante(@PathVariable Long id){
        try{
            adotanteService.deletarAdotante(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao excluir adotante: " + e.getMessage());
        }
    }
}
