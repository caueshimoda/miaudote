package com.miaudote.service;

import com.miaudote.model.Parceiro;
import com.miaudote.model.ParceiroRequest;
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

    public Parceiro cadastrarParceiro(ParceiroRequest request) {
        // 1. Monta o objeto Usuario a partir do DTO
        Usuario usuario = new Usuario();
        usuario.setNome(request.getUsuario().getNome());
        usuario.setEmail(request.getUsuario().getEmail());
        System.out.println(usuario.getEmail());
        usuario.setSenha(request.getUsuario().getSenha()); // será tratada no UsuarioService
        usuario.setNumero(request.getUsuario().getNumero());
        usuario.setComplemento(request.getUsuario().getComplemento());
        usuario.setTelefone(request.getUsuario().getTelefone());
        usuario.setStatus_usr(request.getUsuario().getStatus_usr());

        // 2. Usa o UsuarioService (validação + hash de senha + salvar)
        Usuario savedUsuario = usuarioService.cadastrarUsuario(usuario);

        // 3. Monta o Parceiro vinculado ao mesmo id
        Parceiro parceiro = new Parceiro();
        parceiro.setUsuario(savedUsuario);
        parceiro.setDocumento(request.getDocumento());
        parceiro.setTipo(request.getTipo());
        parceiro.setSite(request.getSite());

        // 4. Salva e retorna
        return parceiroRepository.save(parceiro);
    } 

}
