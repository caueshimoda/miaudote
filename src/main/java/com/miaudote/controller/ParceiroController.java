package com.miaudote.controller;

import com.miaudote.model.Parceiro;
import com.miaudote.model.ParceiroRequest;
import com.miaudote.service.ParceiroService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parceiros")
public class ParceiroController {
    private final ParceiroService parceiroService;

    public ParceiroController(ParceiroService parceiroService) {
        this.parceiroService = parceiroService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Parceiro> cadastrarParceiro(@RequestBody ParceiroRequest request){
        try {
            Parceiro parceiro = parceiroService.cadastrarParceiro(request);
            return new ResponseEntity<>(parceiro, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
