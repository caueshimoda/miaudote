package com.miaudote.model;

import com.miaudote.dto.UsuarioDTO;
import lombok.Getter;

@Getter
public class ParceiroRequest {
    private UsuarioDTO usuario;
    private String documento;
    private Parceiro.Tipo tipo;
    private String site;
}
