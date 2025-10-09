package com.miaudote.service;

import org.springframework.transaction.annotation.Transactional;
import com.miaudote.model.Parceiro;
import com.miaudote.dto.ParceiroRequest;
import com.miaudote.dto.UsuarioCadastroDTO;
import com.miaudote.model.Usuario;
import com.miaudote.repository.UsuarioRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.ParceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Parceiro cadastrarParceiro(ParceiroRequest request) {
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
            System.out.println("DEBUG -> Parceiro mapeado: " + parceiro);


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

}
