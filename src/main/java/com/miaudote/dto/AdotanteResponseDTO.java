package com.miaudote.dto;

import com.miaudote.model.Adotante;
import lombok.Getter;

@Getter
public class AdotanteResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cidade;

    private String estado;

    public AdotanteResponseDTO(Adotante adotante) {
        this.id = adotante.getId();

        if (adotante.getUsuario() != null) {
            this.nome = adotante.getUsuario().getNome();
            this.email = adotante.getUsuario().getEmail();
            this.telefone = adotante.getUsuario().getTelefone();
            if (adotante.getUsuario().getCep() != null){
                this.cidade = adotante.getUsuario().getCep().getCidade();
                this.estado = adotante.getUsuario().getCep().getEstado();
            }
        }
    }

}
