package com.miaudote.controller;

import com.miaudote.model.Cep;
import com.miaudote.service.CepService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ceps")
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Cep> cadastrarCep(@RequestBody Cep request){
        try {
            Cep cep = cepService.cadastrarCep(request);
            return new ResponseEntity<>(cep, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
