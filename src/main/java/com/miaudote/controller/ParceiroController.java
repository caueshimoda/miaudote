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
    public ResponseEntity<Parceiro> cadastrarParceiro(@RequestBody ParceiroCadastroDTO request){
        try {
            Parceiro parceiro = parceiroService.cadastrarParceiro(request);
            return new ResponseEntity<>(parceiro, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParceiroResponseDTO> getParceiroById(@PathVariable Long id) {
        ParceiroResponseDTO parceiroDTO = parceiroService.getParceiro(id);
        
        try {
            return ResponseEntity.ok(parceiroDTO);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Parceiro> atualizarParceiro(@PathVariable Long id, @RequestBody Parceiro parceiro){

        try {
            if(!parceiro.isValidParceiro())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Parceiro parceiroAtualizado = parceiroService.atualizarParceiro(id, parceiro);

            return new ResponseEntity<>(parceiroAtualizado, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarParceiro(@PathVariable Long id){
        try{
            parceiroService.deletarParceiro(id);
            return new ResponseEntity<>(HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

}
