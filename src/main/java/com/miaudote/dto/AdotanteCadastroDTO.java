package com.miaudote.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdotanteCadastroDTO {
    private UsuarioCadastroDTO usuario;
    private String cpf;
    private LocalDate dataNascimento;
}
