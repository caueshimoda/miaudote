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
    public ResponseEntity<?> cadastrarAdocaoSolicitada(@RequestBody AdocaoSolicitadaRequest request){
        try {
            AdocaoSolicitadaResponseDTO adocaoSolicitada = adocaoSolicitadaService.cadastrarAdocaoSolicitada(request);
            return new ResponseEntity<>(adocaoSolicitada, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao cadastrar solicitação: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdocaoSolicitadaById(@PathVariable Long id) {
        AdocaoComFotoDTO adocaoSolicitadaDTO = adocaoSolicitadaService.getAdocaoSolicitada(id);
        
        try {
            return ResponseEntity.ok(adocaoSolicitadaDTO);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar solicitação: " + e.getMessage());
        }
    }

    @GetMapping("/adotante/{id}")
    public ResponseEntity<?> getSolicitacoesByAdotante(@PathVariable Long id) {

        List<AdocaoComFotoDTO> solicitacoes = adocaoSolicitadaService.getSolicitacoesPorAdotante(id);
        
        try {
            return ResponseEntity.ok(solicitacoes);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar solicitações: " + e.getMessage());
        }
    }

    @GetMapping("/parceiro/{id}")
    public ResponseEntity<?> getSolicitacoesByParceiro(@PathVariable Long id) {
        List<AdocaoComFotoDTO> solicitacoes = adocaoSolicitadaService.getSolicitacoesPorParceiro(id);
        
        try {
            return ResponseEntity.ok(solicitacoes);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar solicitações: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarAdocaoSolicitada(@PathVariable Long id, @RequestBody AdocaoSolicitada adocaoSolicitada){
        try {
            if(!adocaoSolicitada.isValidAdocaoSolicitada())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            AdocaoSolicitadaResponseDTO adocaoSolicitadaAtualizada = adocaoSolicitadaService.atualizarAdocaoSolicitada(id, adocaoSolicitada);

            return new ResponseEntity<>(adocaoSolicitadaAtualizada, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao atualizar solicitação: " + e.getMessage());
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
