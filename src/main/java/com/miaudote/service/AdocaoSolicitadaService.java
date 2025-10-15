package com.miaudote.service;

import com.miaudote.dto.AdocaoSolicitadaRequest;
import com.miaudote.dto.AdocaoSolicitadaResponseDTO;
import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Animal;
import com.miaudote.model.Usuario;
import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Adotante;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;

import jakarta.transaction.Transactional;

import com.miaudote.repository.AdocaoSolicitadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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


    public AdocaoSolicitadaResponseDTO cadastrarAdocaoSolicitada(AdocaoSolicitadaRequest request) {
        Adotante adotante = adotanteRepository.findById(request.getAdotanteId())
                .orElseThrow(() -> new RuntimeException("Adotante não encontrado"));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        AdocaoSolicitada solicitacao = new AdocaoSolicitada();
        solicitacao.setAdotante(adotante);
        solicitacao.setAnimal(animal);
        solicitacao.setStatus("Em andamento");

        adocaoSolicitadaRepository.save(solicitacao);

        return new AdocaoSolicitadaResponseDTO(solicitacao);
    }

    public AdocaoSolicitadaResponseDTO getAdocaoSolicitada(Long id) {
        AdocaoSolicitada adocaoSolicitada = adocaoSolicitadaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AdocaoSolicitada não encontrado com id: " + id));
        return new AdocaoSolicitadaResponseDTO(adocaoSolicitada);
    }

    public List<AdocaoSolicitadaResponseDTO> getSolicitacoesPorAdotante(Long adotanteId) {
        List<AdocaoSolicitada> solicitacoes = adocaoSolicitadaRepository.findByAdotanteId(adotanteId);

        return solicitacoes.stream()
               .map(AdocaoSolicitadaResponseDTO::new)
               .toList();
    }

    public List<AdocaoSolicitadaResponseDTO> getSolicitacoesPorParceiro(Long parceiroId) {
        List<AdocaoSolicitada> solicitacoes = adocaoSolicitadaRepository.findByAnimalParceiroId(parceiroId);

        return solicitacoes.stream()
               .map(AdocaoSolicitadaResponseDTO::new)
               .toList();
    }

    public AdocaoSolicitadaResponseDTO atualizarAdocaoSolicitada(Long id, AdocaoSolicitada novosDados){
        AdocaoSolicitada adocaoSolicitadaExistente = adocaoSolicitadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        Optional.ofNullable(novosDados.getStatus()).ifPresent(adocaoSolicitadaExistente::setStatus);
        Optional.ofNullable(novosDados.getDataFinalizacao()).ifPresent(adocaoSolicitadaExistente::setDataFinalizacao);

        if (adocaoSolicitadaExistente.isValidAdocaoSolicitada()) {
            adocaoSolicitadaRepository.save(adocaoSolicitadaExistente);
        }

        return new AdocaoSolicitadaResponseDTO(adocaoSolicitadaExistente);
    }

    public void deletarAdocaoSolicitada(Long id){
        AdocaoSolicitada adocaoSolicitada = adocaoSolicitadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        // Acho que pra essa classe não precisaria disso, mas deixei por precaução
        try {
            adocaoSolicitadaRepository.delete(adocaoSolicitada);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: solicitação vinculada a outros registros", e);
        }
    }
   
}

