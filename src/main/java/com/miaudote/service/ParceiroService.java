package com.miaudote.service;

import org.springframework.transaction.annotation.Transactional;
import com.miaudote.model.Parceiro;
import com.miaudote.dto.ParceiroCadastroDTO;
import com.miaudote.dto.ParceiroResponseDTO;
import com.miaudote.dto.UsuarioCadastroDTO;
import com.miaudote.jwt.UsuarioLogado;
import com.miaudote.model.Usuario;
import com.miaudote.repository.ParceiroRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class ParceiroService {

    private final UsuarioService usuarioService;
    private final ParceiroRepository parceiroRepository;

    public ParceiroService(UsuarioService usuarioService, ParceiroRepository parceiroRepository) {
        this.usuarioService = usuarioService;
        this.parceiroRepository = parceiroRepository;
    }

    @Transactional
    public Parceiro cadastrarParceiro(ParceiroCadastroDTO request) {
        
        if (parceiroRepository.existsByDocumento(request.getDocumento()))
                throw new IllegalArgumentException("Documento já cadastrado.");
        
        Usuario savedUsuario = null;

        try {
            savedUsuario = usuarioService.cadastrarUsuario(request.getUsuario());

            Parceiro parceiro = new Parceiro();
            parceiro.setUsuario(savedUsuario);
            parceiro.setDocumento(request.getDocumento());
            parceiro.setTipo(request.getTipo());
            parceiro.setSite(request.getSite());

            if (!parceiro.isValidDocumento())
                throw new IllegalArgumentException("Documento inválido.");
            
            return parceiroRepository.save(parceiro);

        } catch (Exception e) {

            if (savedUsuario != null && savedUsuario.getId() != null) {
                usuarioService.deletarUsuario(savedUsuario.getId());
            }

            throw new RuntimeException("Erro ao cadastrar parceiro: " + e.getMessage(), e);
        }
    }

    public ParceiroResponseDTO getParceiro(Long id) {
        Parceiro parceiro = parceiroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parceiro não encontrado."));

        return new ParceiroResponseDTO(parceiro);
    }

    @Transactional
    public Parceiro atualizarParceiro(Long id, ParceiroCadastroDTO novosDados){
        Parceiro parceiroExistente = parceiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parceiro não encontrado"));

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(parceiroExistente.getId()))
            throw new AccessDeniedException("Usuário não autorizado a atualizar esse parceiro.");

        //todo: criar método separado para o merge de dados novos com os existentes
        UsuarioCadastroDTO novoUsuario = novosDados.getUsuario();
        Optional.ofNullable(novoUsuario)
            .ifPresent(usuario -> {
                usuarioService.atualizarUsuario(id, usuario); 
            });
            // Não vamos deixar atualizar documento
            // Optional.ofNullable(novosDados.getDocumento()).ifPresent(parceiroExistente::setDocumento); 
            
        Optional.ofNullable(novosDados.getSite()).ifPresent(parceiroExistente::setSite);

        return parceiroRepository.save(parceiroExistente);
    }

    @Transactional 
    public void deletarParceiro(Long id){
        Parceiro parceiro = parceiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parceiro não encontrado"));

        Long idUsuarioLogado = UsuarioLogado.getIdUsuarioLogado();

        if (idUsuarioLogado == null || !idUsuarioLogado.equals(parceiro.getId()))
            throw new AccessDeniedException("Usuário não autorizado a excluir esse parceiro.");

        try {
            parceiroRepository.delete(parceiro); 
            usuarioService.deletarUsuario(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: parceiro vinculado a outros registros", e);
        }
    }

}
