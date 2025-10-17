package com.miaudote.dto;

import java.util.Objects;

import com.miaudote.model.Adotante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdotanteResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cidade;

    private String estado;

    public AdotanteResponseDTO(Adotante adotante) {
        Objects.requireNonNull(adotante, "A entidade Adotante não pode ser nula ao criar AdotanteResponseDTO.");
        Objects.requireNonNull(adotante.getUsuario(), "A entidade Usuario não pode ser nula ao criar AdotanteResponseDTO.");

        this.id = adotante.getId();
        this.nome = adotante.getUsuario().getNome();
        this.email = adotante.getUsuario().getEmail();
        this.telefone = adotante.getUsuario().getTelefone();
        this.cidade = adotante.getUsuario().getCidade();
        this.estado = adotante.getUsuario().getEstado();
    
    }

}
