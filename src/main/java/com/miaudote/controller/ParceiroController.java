package com.miaudote.controller;

import com.miaudote.model.Parceiro;
import com.miaudote.dto.ParceiroCadastroDTO;
import com.miaudote.dto.ParceiroResponseDTO;
import com.miaudote.service.ParceiroService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/parceiros")
public class ParceiroController {
    private final ParceiroService parceiroService;

    public ParceiroController(ParceiroService parceiroService) {
        this.parceiroService = parceiroService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarParceiro(@RequestBody ParceiroCadastroDTO request){
        try {
            Parceiro parceiro = parceiroService.cadastrarParceiro(request);
            return new ResponseEntity<>(parceiro, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParceiroById(@PathVariable Long id) {   
        try {
            ParceiroResponseDTO parceiroDTO = parceiroService.getParceiro(id);
            return ResponseEntity.ok(parceiroDTO);
        }catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao requisitar parceiro: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParceiro(@PathVariable Long id, @RequestBody ParceiroCadastroDTO parceiro){
        try {

            Parceiro parceiroAtualizado = parceiroService.atualizarParceiro(id, parceiro);

            return new ResponseEntity<>(parceiroAtualizado, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao atualizar parceiro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarParceiro(@PathVariable Long id){
        try{
            parceiroService.deletarParceiro(id);
            return new ResponseEntity<>(HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace(); 
                return ResponseEntity.badRequest().body("Erro ao excluir parceiro: " + e.getMessage());
            }
        }

}
