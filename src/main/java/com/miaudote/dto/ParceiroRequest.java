package com.miaudote.dto;

import com.miaudote.model.Parceiro;
import lombok.Getter;

@Getter
public class ParceiroRequest {
    private UsuarioCadastroDTO usuario;
    private String documento;
    private Parceiro.Tipo tipo;
    private String site;
}
