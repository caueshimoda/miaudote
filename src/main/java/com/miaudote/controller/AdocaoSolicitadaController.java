package com.miaudote.controller;

import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.dto.AdocaoComFotoDTO;
import com.miaudote.dto.AdocaoSolicitadaRequest;
import com.miaudote.dto.AdocaoSolicitadaResponseDTO;
import com.miaudote.service.AdocaoSolicitadaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adocoes")
public class AdocaoSolicitadaController {

    private final AdocaoSolicitadaService adocaoSolicitadaService;

    public AdocaoSolicitadaController(AdocaoSolicitadaService adocaoSolicitadaService) {
        this.adocaoSolicitadaService = adocaoSolicitadaService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<AdocaoSolicitadaResponseDTO> cadastrarAdocaoSolicitada(@RequestBody AdocaoSolicitadaRequest request){
        try {
            AdocaoSolicitadaResponseDTO adocaoSolicitada = adocaoSolicitadaService.cadastrarAdocaoSolicitada(request);
            return new ResponseEntity<>(adocaoSolicitada, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdocaoComFotoDTO> getAdocaoSolicitadaById(@PathVariable Long id) {
        AdocaoComFotoDTO adocaoSolicitadaDTO = adocaoSolicitadaService.getAdocaoSolicitada(id);
        
        try {
            return ResponseEntity.ok(adocaoSolicitadaDTO);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/adotante/{id}")
    public ResponseEntity<List<AdocaoComFotoDTO>> getSolicitacoesByAdotante(@PathVariable Long id) {

        List<AdocaoComFotoDTO> solicitacoes = adocaoSolicitadaService.getSolicitacoesPorAdotante(id);
        
        try {
            return ResponseEntity.ok(solicitacoes);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/parceiro/{id}")
    public ResponseEntity<List<AdocaoComFotoDTO>> getSolicitacoesByParceiro(@PathVariable Long id) {
        List<AdocaoComFotoDTO> solicitacoes = adocaoSolicitadaService.getSolicitacoesPorParceiro(id);
        
        try {
            return ResponseEntity.ok(solicitacoes);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdocaoSolicitadaResponseDTO> atualizarAdocaoSolicitada(@PathVariable Long id, @RequestBody AdocaoSolicitada adocaoSolicitada){
        try {
            if(!adocaoSolicitada.isValidAdocaoSolicitada())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            AdocaoSolicitadaResponseDTO adocaoSolicitadaAtualizada = adocaoSolicitadaService.atualizarAdocaoSolicitada(id, adocaoSolicitada);

            return new ResponseEntity<>(adocaoSolicitadaAtualizada, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarAdocaoSolicitada(@PathVariable Long id){
        try{
            adocaoSolicitadaService.deletarAdocaoSolicitada(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
