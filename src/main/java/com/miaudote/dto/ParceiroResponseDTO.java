package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.Parceiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
        Objects.requireNonNull(parceiro, "A entidade Parceiro não pode ser nula ao criar ParceiroResponseDTO.");
        Objects.requireNonNull(parceiro.getUsuario(), "A entidade Usuario não pode ser nula ao criar ParceiroResponseDTO.");

        this.id = parceiro.getId();
        this.site = parceiro.getSite();
        this.tipo = parceiro.getTipo().name();
        this.nome = parceiro.getUsuario().getNome();
        this.email = parceiro.getUsuario().getEmail();
        this.telefone = parceiro.getUsuario().getTelefone();
        this.cidade = parceiro.getUsuario().getCidade();
        this.estado = parceiro.getUsuario().getEstado();
    }

}
