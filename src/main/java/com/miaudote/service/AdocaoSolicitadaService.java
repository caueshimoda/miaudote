package com.miaudote.service;

import com.miaudote.dto.AdocaoComFotoDTO;
import com.miaudote.dto.AdocaoSolicitadaRequest;
import com.miaudote.dto.AdocaoSolicitadaResponseDTO;
import com.miaudote.dto.FotoResponseDTO;
import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Animal;
import com.miaudote.model.Adotante;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;

import com.miaudote.repository.AdocaoSolicitadaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdocaoSolicitadaService {

    private final AdocaoSolicitadaRepository adocaoSolicitadaRepository;
    private final AnimalRepository animalRepository;
    private final AdotanteRepository adotanteRepository;
    private final FotoService fotoService; 

    public AdocaoSolicitadaService(AdocaoSolicitadaRepository adocaoSolicitadaRepository, AnimalRepository animalRepository, AdotanteRepository adotanteRepository, FotoService fotoService) {
        this.adocaoSolicitadaRepository = adocaoSolicitadaRepository;
        this.animalRepository = animalRepository;
        this.adotanteRepository = adotanteRepository;
        this.fotoService = fotoService;
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

    public AdocaoComFotoDTO getAdocaoSolicitada(Long id) {
        AdocaoSolicitada adocaoSolicitada = adocaoSolicitadaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AdocaoSolicitada não encontrado com id: " + id));

        FotoResponseDTO foto = fotoService.getPrimeiraFotoDoAnimal(adocaoSolicitada.getAnimal().getId(), true);
        return new AdocaoComFotoDTO(adocaoSolicitada, foto);
    }

    public List<AdocaoComFotoDTO> getSolicitacoesPorAdotante(Long adotanteId) {
        List<AdocaoSolicitada> solicitacoes = adocaoSolicitadaRepository.findByAdotanteId(adotanteId);

        List<AdocaoComFotoDTO> dtos = new ArrayList<>();

        for (AdocaoSolicitada adocao: solicitacoes) {
            FotoResponseDTO foto = fotoService.getPrimeiraFotoDoAnimal(adocao.getAnimal().getId(), true);
            dtos.add(new AdocaoComFotoDTO(adocao, foto));
        }

        return dtos;
    }

    public List<AdocaoComFotoDTO> getSolicitacoesPorParceiro(Long parceiroId) {
        List<AdocaoSolicitada> solicitacoes = adocaoSolicitadaRepository.findByAnimalParceiroId(parceiroId);

        List<AdocaoComFotoDTO> dtos = new ArrayList<>();

        for (AdocaoSolicitada adocao: solicitacoes) {
            FotoResponseDTO foto = fotoService.getPrimeiraFotoDoAnimal(adocao.getAnimal().getId(), true);
            dtos.add(new AdocaoComFotoDTO(adocao, foto));
        }

        return dtos;
    }

    public AdocaoSolicitadaResponseDTO atualizarAdocaoSolicitada(Long id, AdocaoSolicitada novosDados){
        AdocaoSolicitada adocaoSolicitadaExistente = adocaoSolicitadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        Optional.ofNullable(novosDados.getStatus()).ifPresent(adocaoSolicitadaExistente::setStatus);

        if (!adocaoSolicitadaExistente.isValidStatus()) 
            throw new IllegalArgumentException("Status da adoção inválido.");    

        if (novosDados.getStatus() != null && novosDados.getStatus().substring(0, "Finalizada".length()).equals("Finalizada"))
            adocaoSolicitadaExistente.setDataFinalizacao(LocalDate.now());

        adocaoSolicitadaRepository.save(adocaoSolicitadaExistente);

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

