package com.miaudote.service;

import com.miaudote.model.Foto;
import com.miaudote.dto.FotoResponseDTO;
import com.miaudote.model.Adotante;
import com.miaudote.model.Animal;
import com.miaudote.model.Favorito;
import com.miaudote.repository.FotoRepository;

import jakarta.transaction.Transactional;

import com.miaudote.repository.AdotanteRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.FavoritoRepository;

import org.springframework.data.domain.Page;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.IOException;

@Service
public class FotoService {

    private final FotoRepository fotoRepository;
    private final AnimalRepository animalRepository;
    private final FavoritoRepository favoritoRepository;
    private final AdotanteRepository adotanteRepository;
    private final int FOTOS_POR_PAGINA_ALL = 8;
    private final int FOTOS_POR_PAGINA_PARCEIRO = 7;

    public FotoService(FotoRepository fotoRepository, AnimalRepository animalRepository, FavoritoRepository favoritoRepository, AdotanteRepository adotanteRepository) {
        this.fotoRepository = fotoRepository;
        this.animalRepository = animalRepository;
        this.favoritoRepository = favoritoRepository;
        this.adotanteRepository = adotanteRepository;
    }

    // Como é uma lista de fotos, precisa ter o @Transactional pra ele desfazer o cadastro caso haja erro em alguma 
    @Transactional
    public List<Foto> cadastrarFotos(Long animalId, List<MultipartFile> arquivos) throws IOException {

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        if (fotoRepository.countByAnimalId(animalId) + arquivos.size() > 5) {
            throw new RuntimeException("Não é possível adicionar essa quantidade de fotos ao animal");
        }

        return arquivos.stream().map(arquivo -> {
                try {
                    Foto foto = new Foto();
                    foto.setAnimal(animal);
                    foto.setFoto(arquivo.getBytes()); 

                    return fotoRepository.save(foto);
                } catch (IOException e) {
                    
                    throw new RuntimeException("Falha ao processar o arquivo: " + arquivo.getOriginalFilename(), e);
                }
            }).toList();

    }

    public FotoResponseDTO getFoto(Long fotoId) {
        Foto foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada com ID: " + fotoId));
        
        return new FotoResponseDTO(foto, foto.getAnimal());
    }

    public List<FotoResponseDTO> getFotosPorAnimal(Long animalId) {
        List<Foto> fotos = fotoRepository.findByAnimalId(animalId);

        List<FotoResponseDTO> dtos = new ArrayList<>();
    
        // Depois de usar várias funções sofisticadas, vamos do bom e velho loop pra fazer funcionar essa lógica :D
        // A ideia é mandar os dados do animal só na primeira foto, pra não duplicar dados
        for (int i = 0; i < fotos.size(); i++) {
            Foto foto = fotos.get(i);
            
            if (i == 0) {
                // Usar o construtor que inclui os dados do animal quando for a primeira foto
                dtos.add(new FotoResponseDTO(foto, foto.getAnimal()));
            } else {
                // Para as outras: usa o construtor que não inclui os dados do animal
                dtos.add(new FotoResponseDTO(foto)); 
            }
        }
        
        return dtos;
    }

    public FotoResponseDTO getPrimeiraFotoDoAnimal(Long animalId, boolean pegarDados) {
        Foto foto = fotoRepository.findFirstByAnimalIdOrderByIdAsc(animalId)
                .orElseThrow(() -> new RuntimeException("Nenhuma foto encontrada para o animal ID: " + animalId));
        if (pegarDados)
            return new FotoResponseDTO(foto, foto.getAnimal());
        return new FotoResponseDTO(foto);
    }

    public Pageable getPageable(int pagina, int fotosPorPagina) {
        if (pagina < 1)
            throw new IllegalArgumentException("A página deve ser maior ou igual a 1");

        pagina -= 1;

        Pageable pageable = PageRequest.of(
            pagina,  
            fotosPorPagina,       
            Sort.by("id").ascending() 
        );

        return pageable;

    }

    public List<FotoResponseDTO> getFotosPorParceiro(Long parceiroId, int pagina) {

        Pageable pageable = getPageable(pagina, FOTOS_POR_PAGINA_PARCEIRO);

        Page<Animal> paginas = animalRepository.findPaginasByParceiroId(pageable, parceiroId);
        int totalPaginas = paginas.getTotalPages();

        List<Animal> animais = paginas.getContent();

        List<FotoResponseDTO> fotos = new ArrayList<>();

        boolean pegarDados = true;

        for (Animal animal : animais) {
            // Só vai pegar os dados do primeiro animal da página
            FotoResponseDTO foto = getPrimeiraFotoDoAnimal(animal.getId(), pegarDados);
            foto.setTotalPaginas(totalPaginas);
            fotos.add(foto);
            pegarDados = false;
        }

        return fotos;
    }

    public List<FotoResponseDTO> getFotosPorPagina(Long usuarioId, int pagina) {

        Pageable pageable = getPageable(pagina, FOTOS_POR_PAGINA_ALL);

        Page<Animal> paginas = animalRepository.findAll(pageable);
        int totalPaginas = paginas.getTotalPages();

        List<Animal> animais = paginas.getContent();

        List<FotoResponseDTO> dtos = new ArrayList<FotoResponseDTO>();

        Optional<Adotante> adotanteOptional = Optional.ofNullable(usuarioId)
            .flatMap(id -> adotanteRepository.findById(id));

        Adotante adotante = adotanteOptional.orElse(null);

        boolean isFavorito = false;

        for (Animal animal: animais) {
            FotoResponseDTO foto = getPrimeiraFotoDoAnimal(animal.getId(), true);
            if (adotante != null){
                isFavorito = favoritoRepository.existsByAnimalAndAdotante(animal, adotante);
            }
            foto.setTotalPaginas(totalPaginas);
            foto.setFavorito(isFavorito);
            dtos.add(foto);
        }

        return dtos;

    }

    public List<FotoResponseDTO> getFotosFavoritos(Long adotanteId, int pagina) {

        Pageable pageable = getPageable(pagina, FOTOS_POR_PAGINA_ALL);

        Page<Favorito> paginas = favoritoRepository.findPaginasByAdotanteId(pageable, adotanteId);
        int totalPaginas = paginas.getTotalPages();

        List<Favorito> favoritos = paginas.getContent();

        List<FotoResponseDTO> fotos = new ArrayList<>();

        for (Favorito favorito : favoritos) {
            FotoResponseDTO foto = getPrimeiraFotoDoAnimal(favorito.getAnimal().getId(), true);
            foto.setTotalPaginas(totalPaginas);
            fotos.add(foto);
        }

        return fotos;
    }

    public void deletarFoto(Long id, Long animalId){
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        if (fotoRepository.countByAnimalId(animalId) < 2) {
            throw new RuntimeException("Não é possível excluir a foto, o animal precisa ter pelo menos uma");
        }

        // Acho que pra essa classe não precisaria disso, mas deixei por precaução
        try {
            fotoRepository.delete(foto);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: foto vinculado a outros registros", e);
        }
    }

}
