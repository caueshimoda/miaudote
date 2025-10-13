package com.miaudote.controller;

import com.miaudote.model.Favorito;
import com.miaudote.dto.FavoritoRequest;
import com.miaudote.service.FavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Favorito> cadastrarFavorito(@RequestBody FavoritoRequest request){
        try {
            Favorito favorito = favoritoService.cadastrarFavorito(request);
            return new ResponseEntity<>(favorito, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
