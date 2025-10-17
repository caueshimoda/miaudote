package com.miaudote.dto;

import com.miaudote.model.Parceiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParceiroCadastroDTO {
    private UsuarioCadastroDTO usuario;
    private String documento;
    private Parceiro.Tipo tipo;
    private String site;
}
