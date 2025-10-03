package com.miaudote.model;

import lombok.Getter;

@Getter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;       // vai ser validada e depois transformada em hash
    private int numero;
    private String complemento;
    private String telefone;
    private Usuario.StatusUsuario status_usr;
}
