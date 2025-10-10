package com.miaudote.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class AdotanteRequest {
    private UsuarioCadastroDTO usuario;
    private String cpf;
    private LocalDate dataNascimento;
}
