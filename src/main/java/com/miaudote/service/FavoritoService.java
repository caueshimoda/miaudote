package com.miaudote.service;

import com.miaudote.dto.FavoritoResponseDTO;
import com.miaudote.jwt.UsuarioLogado;
import com.miaudote.dto.FavoritoRequest;
import com.miaudote.model.Favorito;
import com.miaudote.model.Animal;
import com.miaudote.model.Adotante;
import com.miaudote.repository.FavoritoRepository;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final AnimalRepository animalRepository;
    private final AdotanteRepository adotanteRepository;

    public FavoritoService(FavoritoRepository favoritoRepository, AnimalRepository animalRepository, AdotanteRepository adotanteRepository) {
        this.favoritoRepository = favoritoRepository;
        this.animalRepository = animalRepository;
        this.adotanteRepository = adotanteRepository;
    }


    public FavoritoResponseDTO cadastrarFavorito(FavoritoRequest request) {
        Adotante adotante = adotanteRepository.findById(request.getAdotanteId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(adotante.getId()))
            throw new AccessDeniedException("Usuário não autorizado a adicionar favoritos para esse adotante.");

        if (favoritoRepository.existsByAnimalAndAdotante(animal, adotante))
            throw new RuntimeException("O adotante já favoritou esse animal");

        Favorito favorito = new Favorito();
        favorito.setAdotante(adotante);
        favorito.setAnimal(animal);

        favoritoRepository.save(favorito);

        return new FavoritoResponseDTO(favorito);
    }

     public FavoritoResponseDTO getFavorito(Long id) {
        Favorito favorito = favoritoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorito não encontrado com id: " + id));

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (favorito.getAdotante() == null)
            throw new RuntimeException("Favorito com adotante inválido.");

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(favorito.getAdotante().getId()))
            throw new AccessDeniedException("Usuário não autorizado a acessar os favoritos desse adotante.");

        return new FavoritoResponseDTO(favorito);
    }

    public List<FavoritoResponseDTO> getFavoritosPorAdotante(Long adotanteId) {
        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(adotanteId))
            throw new AccessDeniedException("Usuário não autorizado a acessar os favoritos desse adotante.");

        List<Favorito> favoritos = favoritoRepository.findByAdotanteId(adotanteId);

        return favoritos.stream()
               .map(FavoritoResponseDTO::new)
               .toList();
    }

    public void deletarFavorito(Long id){
        Favorito favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
        
        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (favorito.getAdotante() == null)
            throw new RuntimeException("Favorito com adotante inválido.");

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(favorito.getAdotante().getId()))
            throw new AccessDeniedException("Usuário não autorizado a excluir os favoritos desse adotante.");

        // Acho que pra essa classe não precisaria disso, mas deixei por precaução
        try {
            favoritoRepository.delete(favorito);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: favorito vinculado a outros registros", e);
        }
    }
    

}
