package com.miaudote.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.miaudote.model.AdocaoSolicitada;
import com.miaudote.model.StatusAdocao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdocaoSolicitadaResponseDTO {

    private Long id; 
    private AdotanteResponseDTO adotante;
    private AnimalResponseDTO animal;
    private String status;
    private String cpf;
    LocalDate dataCadastro;

    public AdocaoSolicitadaResponseDTO(AdocaoSolicitada adocaoSolicitada) {
        Objects.requireNonNull(adocaoSolicitada, "A entidade Adoção Solicitada não pode ser nula ao criar AdocaoSolicitadalResponseDTO.");

        this.id = adocaoSolicitada.getId();
        this.adotante = new AdotanteResponseDTO(adocaoSolicitada.getAdotante());
        this.animal = new AnimalResponseDTO(adocaoSolicitada.getAnimal());
        this.dataCadastro = adocaoSolicitada.getDataCadastro().toLocalDate();
        if (adocaoSolicitada.getAdotante() != null)
            this.cpf = adocaoSolicitada.getAdotante().getCpf();

        if (adocaoSolicitada.getStatus() == null) 
            this.status = "Indefinido"; 
        else if (StatusAdocao.isAberta(adocaoSolicitada.getStatus())) 
            this.status = "Em Aberto"; 
        else if (StatusAdocao.isEncerrada(adocaoSolicitada.getStatus())) 
            this.status = "Encerrada";
        else
            this.status = "Cancelada";
    }

}
