package com.miaudote.service;

import com.miaudote.dto.AdocaoComFotoDTO;
import com.miaudote.dto.AdocaoSolicitadaRequest;
import com.miaudote.dto.AdocaoSolicitadaResponseDTO;
import com.miaudote.dto.FavoritoRequest;
import com.miaudote.dto.FotoResponseDTO;
import com.miaudote.jwt.UsuarioLogado;
import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.Animal;
import com.miaudote.model.StatusAdocao;
import com.miaudote.model.Adotante;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.FavoritoRepository;
import com.miaudote.repository.AdocaoSolicitadaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
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
    private final FavoritoRepository favoritoRepository;
    private final FotoService fotoService; 
    private final FavoritoService favoritoService; 

    public AdocaoSolicitadaService(AdocaoSolicitadaRepository adocaoSolicitadaRepository, AnimalRepository animalRepository, 
                                    AdotanteRepository adotanteRepository, FotoService fotoService, 
                                    FavoritoRepository favoritoRepository, FavoritoService favoritoService) {
       
        this.adocaoSolicitadaRepository = adocaoSolicitadaRepository;
        this.animalRepository = animalRepository;
        this.adotanteRepository = adotanteRepository;
        this.fotoService = fotoService;
        this.favoritoRepository = favoritoRepository;
        this.favoritoService = favoritoService;
    }


    public AdocaoSolicitadaResponseDTO cadastrarAdocaoSolicitada(AdocaoSolicitadaRequest request) {
        Adotante adotante = adotanteRepository.findById(request.getAdotanteId())
                .orElseThrow(() -> new RuntimeException("Adotante não encontrado."));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado."));

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(adotante.getId()))
            throw new AccessDeniedException("O usuário não pode criar solicitações para esse adotante.");

        if (!animal.isDisponivel())
            throw new RuntimeException("Animal indisponível para adoção.");

        if (adocaoSolicitadaRepository.existsByAdotanteAndAnimalAndStatus(adotante, animal, StatusAdocao.EM_ANDAMENTO)) 
            throw new RuntimeException("Já existe uma solicitação em aberto para esse animal vindo do adotante.");

        // Ao abrir uma solicitação, o animal vira favorito do adotante automaticamente, se já não era
        if (!favoritoRepository.existsByAnimalAndAdotante(animal, adotante)) 
            favoritoService.cadastrarFavorito(new FavoritoRequest(adotante.getId(), animal.getId()));
    

        AdocaoSolicitada solicitacao = new AdocaoSolicitada();
        solicitacao.setAdotante(adotante);
        solicitacao.setAnimal(animal);
        solicitacao.setStatus(StatusAdocao.EM_ANDAMENTO);

        adocaoSolicitadaRepository.save(solicitacao);

        return new AdocaoSolicitadaResponseDTO(solicitacao);
    }

    public AdocaoComFotoDTO getAdocaoSolicitada(Long id) {
        AdocaoSolicitada adocaoSolicitada = adocaoSolicitadaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrado com id: " + id));

        if (adocaoSolicitada.getAnimal() == null || adocaoSolicitada.getAnimal().getParceiro() == null || adocaoSolicitada.getAdotante() == null)
            throw new RuntimeException("Solicitação de adoção inválida.");

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        boolean naoAutorizado = idUsuarioLogado == null || (!idUsuarioLogado.equals(adocaoSolicitada.getAnimal().getParceiro().getId()) && !idUsuarioLogado.equals(adocaoSolicitada.getAdotante().getId()));

        if (naoAutorizado)
            throw new AccessDeniedException("O usuário não pode acessar essa solicitação.");

        List<FotoResponseDTO> fotos = fotoService.getFotosPorAnimal(adocaoSolicitada.getAnimal().getId());
        return new AdocaoComFotoDTO(adocaoSolicitada, fotos);
    }

    public List<AdocaoComFotoDTO> getSolicitacoesPorAdotante(Long adotanteId) {

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(adotanteId))
            throw new AccessDeniedException("O usuário não pode acessar as solicitações desse adotante.");
        List<AdocaoSolicitada> solicitacoes = adocaoSolicitadaRepository.findByAdotanteId(adotanteId);

        List<AdocaoComFotoDTO> dtos = new ArrayList<>();

        for (AdocaoSolicitada adocao: solicitacoes) {
            // Estou criando essa lista pra mandar uma lista de uma foto só e evitar um novo DTO...
            // Sim...
            List<FotoResponseDTO> foto = new ArrayList<>();  
            foto.add(fotoService.getPrimeiraFotoDoAnimal(adocao.getAnimal().getId(), true));
            dtos.add(new AdocaoComFotoDTO(adocao, foto));
        }

        return dtos;
    }

    public List<AdocaoComFotoDTO> getSolicitacoesPorParceiro(Long parceiroId) {

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(parceiroId))
            throw new AccessDeniedException("O usuário não pode acessar as solicitações desse parceiro.");

        List<AdocaoSolicitada> solicitacoes = adocaoSolicitadaRepository.findByAnimalParceiroId(parceiroId);

        List<AdocaoComFotoDTO> dtos = new ArrayList<>();

        for (AdocaoSolicitada adocao: solicitacoes) {
            List<FotoResponseDTO> foto = new ArrayList<>();  
            foto.add(fotoService.getPrimeiraFotoDoAnimal(adocao.getAnimal().getId(), true));
            dtos.add(new AdocaoComFotoDTO(adocao, foto));
        }

        return dtos;
    }

    public AdocaoSolicitadaResponseDTO atualizarAdocaoSolicitada(Long id, AdocaoSolicitada novosDados){

        AdocaoSolicitada adocaoSolicitadaExistente = adocaoSolicitadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada."));

        if (adocaoSolicitadaExistente.getAnimal() == null || adocaoSolicitadaExistente.getAnimal().getParceiro() == null || adocaoSolicitadaExistente.getAdotante() == null)
            throw new RuntimeException("Solicitação de adoção inválida.");

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        boolean naoAutorizado = idUsuarioLogado == null || (!idUsuarioLogado.equals(adocaoSolicitadaExistente.getAnimal().getParceiro().getId()) && !idUsuarioLogado.equals(adocaoSolicitadaExistente.getAdotante().getId()));

        if (naoAutorizado)
            throw new AccessDeniedException("O usuário não pode atualizar essa solicitação.");

        if (!StatusAdocao.isAberta(adocaoSolicitadaExistente.getStatus()))
            throw new RuntimeException("Soliticações finalizadas não podem ser atualizadas.");

        Optional.ofNullable(novosDados.getStatus()).ifPresent(adocaoSolicitadaExistente::setStatus);

        if (!adocaoSolicitadaExistente.isValidStatus()) 
            throw new IllegalArgumentException("Status da adoção inválido.");    

        if (novosDados.getStatus() != null && !StatusAdocao.isAberta(novosDados.getStatus()))
            adocaoSolicitadaExistente.setDataFinalizacao(LocalDate.now());

        adocaoSolicitadaRepository.save(adocaoSolicitadaExistente);

        return new AdocaoSolicitadaResponseDTO(adocaoSolicitadaExistente);
    }

    public void deletarAdocaoSolicitada(Long id){
        AdocaoSolicitada adocaoSolicitada = adocaoSolicitadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada."));

        if (adocaoSolicitada.getAnimal() == null || adocaoSolicitada.getAnimal().getParceiro() == null || adocaoSolicitada.getAdotante() == null)
            throw new RuntimeException("Solicitação de adoção inválida.");

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        boolean naoAutorizado = idUsuarioLogado == null || (!idUsuarioLogado.equals(adocaoSolicitada.getAnimal().getParceiro().getId()) && !idUsuarioLogado.equals(adocaoSolicitada.getAdotante().getId()));

        if (naoAutorizado)
            throw new AccessDeniedException("O usuário não pode excluir essa solicitação.");


        // Acho que pra essa classe não precisaria disso, mas deixei por precaução
        try {
            adocaoSolicitadaRepository.delete(adocaoSolicitada);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: solicitação vinculada a outros registros.", e);
        }
    }
   
}

