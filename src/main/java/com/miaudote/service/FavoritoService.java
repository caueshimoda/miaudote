package com.miaudote.service;

import com.miaudote.dto.FavoritoResponseDTO;
import com.miaudote.dto.FavoritoRequest;
import com.miaudote.model.Favorito;
import com.miaudote.model.Animal;
import com.miaudote.model.Adotante;
import com.miaudote.repository.FavoritoRepository;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

        Favorito favorito = new Favorito();
        favorito.setAdotante(adotante);
        favorito.setAnimal(animal);

        favoritoRepository.save(favorito);

        return new FavoritoResponseDTO(favorito);
    }

     public FavoritoResponseDTO getFavorito(Long id) {
        Favorito favorito = favoritoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorito não encontrado com id: " + id));
        return new FavoritoResponseDTO(favorito);
    }

    public List<FavoritoResponseDTO> getFavoritosPorAdotante(Long adotanteId) {
        List<Favorito> favoritos = favoritoRepository.findByAdotanteId(adotanteId);

        return favoritos.stream()
               .map(FavoritoResponseDTO::new)
               .toList();
    }

    public void deletarFavorito(Long id){
        Favorito favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        // Acho que pra essa classe não precisaria disso, mas deixei por precaução
        try {
            favoritoRepository.delete(favorito);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: favorito vinculado a outros registros", e);
        }
    }
    

}
