package com.miaudote.service;

import org.springframework.transaction.annotation.Transactional;
import com.miaudote.model.Adotante;
import com.miaudote.dto.AdotanteRequest;
import com.miaudote.dto.UsuarioCadastroDTO;
import com.miaudote.model.Usuario;
import com.miaudote.repository.UsuarioRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.AdotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdotanteService {

    private final UsuarioService usuarioService;
    private final AdotanteRepository adotanteRepository;

    public AdotanteService(UsuarioService usuarioService, AdotanteRepository adotanteRepository) {
        this.usuarioService = usuarioService;
        this.adotanteRepository = adotanteRepository;
    }

    @Transactional
    public Adotante cadastrarAdotante(AdotanteRequest request) {
        Usuario savedUsuario = null;
        try {
            // Cadastra o usuário (isso insere na tabela usuarios)
            savedUsuario = usuarioService.cadastrarUsuario(request.getUsuario());

            // Cria e associa o adotante
            Adotante adotante = new Adotante();
            adotante.setUsuario(savedUsuario);
            adotante.setCpf(request.getCpf());
            adotante.setDataNascimento(request.getDataNascimento());


            // Salva o adotante
            return adotanteRepository.save(adotante);

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar Adotante: " + e.getMessage());
            e.printStackTrace(); // <-- mostra o stack trace completo no console

            if (savedUsuario != null && savedUsuario.getId() != null) {
                usuarioService.deletarUsuario(savedUsuario.getId());
            }

            throw new RuntimeException("Erro ao cadastrar Adotante: " + e.getMessage(), e);
        }
    }

}
