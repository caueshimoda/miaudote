package com.miaudote.controller;

import com.miaudote.dto.FotoResponseDTO;
import com.miaudote.model.Foto;
import com.miaudote.service.FotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fotos")
public class FotoController {

    private final FotoService fotoService;

    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }


    @PostMapping("/cadastrar/{animalId}")
    public ResponseEntity<String> cadastrarFoto(@PathVariable Long animalId, @RequestParam("files") List<MultipartFile> arquivos){
        try {
            fotoService.cadastrarFotos(animalId, arquivos);
            return ResponseEntity.status(HttpStatus.CREATED).body("Foto(s) enviada(s) com sucesso.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ler o arquivo.");
        } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoResponseDTO> getFoto(@PathVariable Long id) {
        FotoResponseDTO fotoDTO = fotoService.getFoto(id);

        try {
            return ResponseEntity.ok(fotoDTO);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/animal/{id}")
    public ResponseEntity<List<FotoResponseDTO>> getFotoPorAnimal(@PathVariable Long id) {
        List<FotoResponseDTO> fotos = fotoService.getFotosPorAnimal(id);

        try {
            return ResponseEntity.ok(fotos);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
