package com.miaudote.dto;

import com.miaudote.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCadastroDTO {
    private String nome;
    private String email;
    private String senha;
    private int numero;
    private String complemento;
    private String telefone;
    private Usuario.TipoUsuario tipoUsuario;
    private String cpf;
    private String cnpj;

    /*

    Aqui vão os atributos essenciais quando o usuário cria uma conta
    Possíveis dúvidas: Por que temos um campo pra CPF e outro pra CNPJ?
        - Ao consultar os universitários (chat gepeto e amigos), foi-me recomendado três opções para tratar os dois
          tipos de usuários que temos (ONG e pessoa física), e a melhor delas (segundo eu), foi criar campos opcionais de CPF/CNPJ,
          que seriam preenchidos de acordo com o tipo de usuário escolhido. Ou seja, joguei a bola pro front-end.
        - でも、小倉さん、あと二つのオプションって何ですか〜？
          As outras duas opções seriam: criar as subclasses PessoaFisica e ONG, mas aí teríamos a camada service quase duplicada, já que a
          única coisa que muda entre elas é um campo; e a outra eu não lembro.

     */
}

