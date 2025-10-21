package com.miaudote.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UsuarioLoginDTO {
    private Long id;
    private String email;
    private String tipo;

    public UsuarioLoginDTO(Long id, String email, String tipo) {
        this.id = id;
        this.email = email;
        this.tipo = tipo;
    }
}

