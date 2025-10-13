package com.miaudote.controller;

import com.miaudote.model.Foto;
import com.miaudote.dto.FotoRequest;
import com.miaudote.service.FotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fotos")
public class FotoController {

    private final FotoService fotoService;

    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Foto> cadastrarFoto(@RequestBody FotoRequest request){
        try {
            Foto Foto = fotoService.cadastrarFoto(request);
            return new ResponseEntity<>(Foto, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
