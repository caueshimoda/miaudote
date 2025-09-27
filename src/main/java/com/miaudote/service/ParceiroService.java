package com.miaudote.service;

import com.miaudote.model.Parceiro;
import com.miaudote.model.Usuario;
import com.miaudote.repository.UsuarioRepository;
import com.miaudote.repository.AnimalRepository;
import com.miaudote.repository.ParceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

public class ParceiroService {

    private final ParceiroRepository parceiroRepository;
    private final UsuarioRepository usuarioRepository;

    public ParceiroService(ParceiroRepository parceiroRepository, UsuarioRepository usuarioRepository) {
        this.parceiroRepository = parceiroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /* TO DO 
    public Parceiro cadastrarParceiro() {

    } */

}
