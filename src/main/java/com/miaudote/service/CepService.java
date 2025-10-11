package com.miaudote.service;

import com.miaudote.dto.CepCadastroDTO;
import com.miaudote.model.Cep;
import com.miaudote.repository.CepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CepService {

    private final CepRepository cepRepository;

    public CepService(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }


    public Cep cadastrarCep(CepCadastroDTO request) {

        Cep cep = new Cep();
        cep.setNumero(request.getNumero());
        cep.setLogradouro(request.getLogradouro());
        cep.setBairro(request.getBairro());
        cep.setCidade(request.getCidade());
        cep.setEstado(request.getEstado());

        return cepRepository.save(cep);
    }

}
