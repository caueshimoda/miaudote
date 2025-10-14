package com.miaudote.dto;

import com.miaudote.model.Parceiro;
import lombok.Getter;

@Getter
public class ParceiroResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cidade;

    private String estado;

    private String tipo;

    private String site;

    public ParceiroResponseDTO(Parceiro parceiro) {
        this.id = parceiro.getId();
        this.site = parceiro.getSite();
        this.tipo = parceiro.getTipo().name();

        if (parceiro.getUsuario() != null) {
            this.nome = parceiro.getUsuario().getNome();
            this.email = parceiro.getUsuario().getEmail();
            this.telefone = parceiro.getUsuario().getTelefone();
            this.cidade = parceiro.getUsuario().getCidade();
            this.estado = parceiro.getUsuario().getEstado();
        }
    }

}
