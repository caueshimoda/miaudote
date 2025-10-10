package com.miaudote.controller;

import com.miaudote.model.Adotante;
import com.miaudote.dto.AdotanteRequest;
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
    public ResponseEntity<Adotante> cadastrarAdotante(@RequestBody AdotanteRequest request){
        try {
            Adotante Adotante = adotanteService.cadastrarAdotante(request);
            return new ResponseEntity<>(Adotante, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }  
}
