package com.miaudote.dto;

import com.miaudote.model.Usuario;
import lombok.Getter;
import lombok.Setter;

// ESSA CLASSE RETORNA DADOS AO FRONT

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Usuario.TipoUsuario tipoUsuario;

    /* Por que a classe não retorna os dados do endereço e cpf/cnpj?
       Eu vi que essa classe serve como resposta ao front, e que podemos escolher quais são os dados mais adequados de enviar
       Dependendo do contexto, seria melhor não deixarmos dados pessoais, como endereço e n° de documento, expostos quando não são necessários
       Ainda estou aprendendo sobre as DTOs também, então tudo está aberto à discussão :)
     */

}
