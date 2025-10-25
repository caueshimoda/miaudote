package com.miaudote.service;

import org.springframework.transaction.annotation.Transactional;
import com.miaudote.model.Adotante;
import com.miaudote.dto.AdotanteCadastroDTO;
import com.miaudote.dto.AdotanteResponseDTO;
import com.miaudote.dto.UsuarioCadastroDTO;
import com.miaudote.jwt.UsuarioLogado;
import com.miaudote.model.Usuario;
import com.miaudote.repository.AdotanteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
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
    public Adotante cadastrarAdotante(AdotanteCadastroDTO request) {
        if (adotanteRepository.existsByCpf(request.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado.");
            
        Usuario savedUsuario = null;
        try {
            savedUsuario = usuarioService.cadastrarUsuario(request.getUsuario());

            Adotante adotante = new Adotante();
            adotante.setUsuario(savedUsuario);
            adotante.setCpf(request.getCpf());
            adotante.setDataNascimento(request.getDataNascimento());

            if (!adotante.isValidCpf())
                throw new IllegalArgumentException("CPF do Adotante inválido, deve ser composto apenas por números e ter 11 dígitos.");

            if (!adotante.isValidDataNascimento())
                throw new IllegalArgumentException("Data de Nascimento do Adotante inválida, não pode estar no futuro.");

            return adotanteRepository.save(adotante);

        } catch (Exception e) {

            if (savedUsuario != null && savedUsuario.getId() != null) {
                usuarioService.deletarUsuario(savedUsuario.getId());
            }

            throw new RuntimeException("Erro ao cadastrar Adotante: " + e.getMessage(), e);
        }
    }

    public AdotanteResponseDTO getAdotante(Long id) {
        Adotante adotante = adotanteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("adotante não encontrado com id: " + id));

        return new AdotanteResponseDTO(adotante);
    }

    @Transactional
    public Adotante atualizarAdotante(Long id, AdotanteCadastroDTO novosDados){

        Adotante adotanteExistente = adotanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("adotante não encontrado"));

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(adotanteExistente.getId()))
            throw new AccessDeniedException("Usuário não autorizado a atualizar esse adotante.");

        UsuarioCadastroDTO novoUsuario = novosDados.getUsuario();
        Optional.ofNullable(novoUsuario)
            .ifPresent(usuario -> {
                usuarioService.atualizarUsuario(id, usuario); 
            });

        return adotanteRepository.save(adotanteExistente);
    }

    @Transactional 
    public void deletarAdotante(Long id){
        Adotante adotante = adotanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adotante não encontrado"));
        
        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(adotante.getId()))
            throw new AccessDeniedException("Usuário não autorizado a excluir esse adotante.");

        try {
            adotanteRepository.delete(adotante);
            usuarioService.deletarUsuario(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: adotante vinculado a outros registros", e);
        }
    }

}
