package com.miaudote.controller;

import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.dto.AdocaoSolicitadaCadastroDTO;
import com.miaudote.dto.AdocaoSolicitadaCadastroDTO;
import com.miaudote.service.AdocaoSolicitadaService;
import com.miaudote.service.AdocaoSolicitadaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adocoes")
public class AdocaoSolicitadaController {

    private final AdocaoSolicitadaService adocaoSolicitadaService;

    public AdocaoSolicitadaController(AdocaoSolicitadaService adocaoSolicitadaService) {
        this.adocaoSolicitadaService = adocaoSolicitadaService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<AdocaoSolicitada> cadastrarAdocaoSolicitada(@RequestBody AdocaoSolicitadaCadastroDTO request){
        try {
            AdocaoSolicitada adocaoSolicitada = adocaoSolicitadaService.cadastrarAdocaoSolicitada(request);
            return new ResponseEntity<>(adocaoSolicitada, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
