package com.miaudote.service;

import org.springframework.transaction.annotation.Transactional;
import com.miaudote.model.Parceiro;
import com.miaudote.dto.ParceiroCadastroDTO;
import com.miaudote.dto.ParceiroResponseDTO;
import com.miaudote.dto.UsuarioCadastroDTO;
import com.miaudote.model.Usuario;
import com.miaudote.repository.UsuarioRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.ParceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
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
        Usuario savedUsuario = null;
        try {
            // Cadastra o usuário (isso insere na tabela usuarios)
            savedUsuario = usuarioService.cadastrarUsuario(request.getUsuario());

            // Cria e associa o parceiro
            Parceiro parceiro = new Parceiro();
            parceiro.setUsuario(savedUsuario);
            //parceiro.setId(savedUsuario.getId());
            parceiro.setDocumento(request.getDocumento());
            parceiro.setTipo(request.getTipo());
            parceiro.setSite(request.getSite());


            // Salva o parceiro
            return parceiroRepository.save(parceiro);

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar parceiro: " + e.getMessage());
            e.printStackTrace(); // <-- mostra o stack trace completo no console

            if (savedUsuario != null && savedUsuario.getId() != null) {
                usuarioService.deletarUsuario(savedUsuario.getId());
            }

            throw new RuntimeException("Erro ao cadastrar parceiro: " + e.getMessage(), e);
        }
    }

    public ParceiroResponseDTO getParceiro(Long id) {
        Parceiro parceiro = parceiroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parceiro não encontrado com id: " + id));

        return new ParceiroResponseDTO(parceiro);
    }

    public Parceiro atualizarParceiro(Long id, Parceiro novosDados){
        Parceiro parceiroExistente = parceiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parceiro não encontrado"));

        //todo: criar método separado para o merge de dados novos com os existentes
        Usuario novoUsuario = novosDados.getUsuario();
        Optional.ofNullable(novoUsuario)
            .ifPresent(usuario -> {
                usuarioService.atualizarUsuario(id, usuario); 
            });
        Optional.ofNullable(novosDados.getDocumento()).ifPresent(parceiroExistente::setDocumento);
        Optional.of(novosDados.getTipo()).ifPresent(parceiroExistente::setTipo);
        Optional.ofNullable(novosDados.getSite()).ifPresent(parceiroExistente::setSite);

        // validação dos novos dados
        if(parceiroExistente.isValidParceiro())
            parceiroRepository.save(parceiroExistente);

        return parceiroExistente;
    }

    @Transactional 
    public void deletarParceiro(Long id){
        Parceiro parceiro = parceiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parceiro não encontrado"));

        try {
            parceiroRepository.delete(parceiro);
            usuarioService.deletarUsuario(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: parceiro vinculado a outros registros", e);
        }
    }

}
