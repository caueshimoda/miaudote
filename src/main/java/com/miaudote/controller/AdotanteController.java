package com.miaudote.controller;

import com.miaudote.model.Adotante;
import com.miaudote.dto.AdotanteCadastroDTO;
import com.miaudote.dto.AdotanteResponseDTO;
import com.miaudote.service.AdotanteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adotantes")
public class AdotanteController {
  private final AdotanteService adotanteService;

    public AdotanteController(AdotanteService adotanteService) {
        this.adotanteService = adotanteService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Adotante> cadastrarAdotante(@RequestBody AdotanteCadastroDTO request){
        try {
            Adotante Adotante = adotanteService.cadastrarAdotante(request);
            return new ResponseEntity<>(Adotante, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }  

    @GetMapping("/{id}")
    public ResponseEntity<AdotanteResponseDTO> getAdotanteById(@PathVariable Long id) {
        AdotanteResponseDTO adotanteDTO = adotanteService.getAdotante(id);
        
        try {
            return ResponseEntity.ok(adotanteDTO);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Adotante> atualizarAdotante(@PathVariable Long id, @RequestBody Adotante adotante){
        try {
            if(!adotante.isValidAdotante())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Adotante adotanteAtualizado = adotanteService.atualizarAdotante(id, adotante);

            return new ResponseEntity<>(adotanteAtualizado, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarAdotante(@PathVariable Long id){
        try{
            adotanteService.deletarAdotante(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
