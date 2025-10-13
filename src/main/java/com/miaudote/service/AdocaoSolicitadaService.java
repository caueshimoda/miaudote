package com.miaudote.service;

import com.miaudote.dto.AdocaoSolicitadaRequest;
import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Animal;
import com.miaudote.model.Adotante;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.AdocaoSolicitadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdocaoSolicitadaService {

    private final AdocaoSolicitadaRepository adocaoSolicitadaRepository;
    private final AnimalRepository animalRepository;
    private final AdotanteRepository adotanteRepository;

    public AdocaoSolicitadaService(AdocaoSolicitadaRepository adocaoSolicitadaRepository, AnimalRepository animalRepository, AdotanteRepository adotanteRepository) {
        this.adocaoSolicitadaRepository = adocaoSolicitadaRepository;
        this.animalRepository = animalRepository;
        this.adotanteRepository = adotanteRepository;
    }


    public AdocaoSolicitada cadastrarAdocaoSolicitada(AdocaoSolicitadaRequest request) {
        Adotante adotante = adotanteRepository.findById(request.getAdotanteId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        AdocaoSolicitada solicitacao = new AdocaoSolicitada();
        solicitacao.setAdotante(adotante);
        solicitacao.setAnimal(animal);
        solicitacao.setStatus("Em andamento");

        return adocaoSolicitadaRepository.save(solicitacao);
    }

   
}

