package com.miaudote.service;

import com.miaudote.dto.FavoritoCadastroDTO;
import com.miaudote.model.Favorito;
import com.miaudote.model.Animal;
import com.miaudote.model.Adotante;
import com.miaudote.repository.FavoritoRepository;
import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public Favorito cadastrarFavorito(FavoritoCadastroDTO request) {
        Adotante adotante = adotanteRepository.findById(request.getAdotanteId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        Favorito favorito = new Favorito();
        favorito.setAdotante(adotante);
        favorito.setAnimal(animal);

        return favoritoRepository.save(favorito);
    }

}
