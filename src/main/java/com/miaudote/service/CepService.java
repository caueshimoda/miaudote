package com.miaudote.service;

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


    public Cep cadastrarCep(Cep cep) {

        return cepRepository.save(cep);
    }

}
