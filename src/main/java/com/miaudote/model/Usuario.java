package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Arrays;

/*
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;*/
import java.util.regex.Pattern;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email; // regex

    @Transient //o campo 'senha' ficará somente na memória para validação, depois é transformada em hash
    private String senha; // regex aqui, autenticação/criptografia em Service
    
    @Column(name = "senha")
    private String senha_hash; // vai para o banco
    private String cidade;
    private String estado;
    private String telefone; // regex
    //private String cpf;
    //private String cnpj;

    /* 
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    public enum TipoUsuario {
        PESSOA_FISICA,
        ONG
    }
    */

    public boolean isValidUsuario(){
        return isValidNome() && isValidEmail() && isValidSenha(senha)
                && isValidCidade() && isValidEstado() && isValidTelefone();
    }


    public boolean isValidNome(){
        return ValidacaoNome.isValidNome(getNome());
    }

    public boolean isValidEmail(){
        final String EMAIL_REGEX =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        String email = getEmail();

        if (email == null || email.isEmpty())
            return false;

        return EMAIL_PATTERN.matcher(email).matches();
    }

    /*
    só vai validar a força da senha, a criptografia e autenticação será feita no Service
    ela precisa ter mais de 8 caracteres (devo colocar um limite tbm?), letras maiusculas, caracteres especiais e numeros
     */
    public boolean isValidSenha(String senha){
        final String PASSWORD_REGEX =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$";
        final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        if (senha == null || senha.isEmpty())
            return false;

        return PASSWORD_PATTERN.matcher(senha).matches();

    }

    public boolean isValidCidade(){
        return ValidacaoNome.isValidNome(getCidade());
    }

    public boolean isValidEstado(){
        try {
            if (estado == null || estado.isEmpty())
                return false;

            List<String> estados = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", 
                                                "DF", "ES", "GO", "MA", "MT", "MS", "MG",
                                                "PA", "PB", "PR", "PE", "PI", "RJ", "RN", 
                                                "RS", "RO", "RR", "SC", "SP", "SE", "TO");
            return estados.contains(estado);

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidTelefone(){
        //o regex só vai validar o número puro, sem - ou (), ele precisa conter um ddd e um 9, e depois 8 digitos quaisquer
        final String TELEFONE_REGEX = "^(\\d{2})9\\d{8}$";
        final Pattern TELEFONE_PATTERN = Pattern.compile(TELEFONE_REGEX);

        String telefone = getTelefone();

        try {
            if (telefone == null || telefone.isEmpty())
                return false;
        } catch (Exception e){
            return false;
        }
        return TELEFONE_PATTERN.matcher(telefone).matches();
    }


    /*
    considerações a fazer:
        podem ignorar esse metodo, as vozes da minha cabeça vão saber o que fazer com ele no futuro

     public boolean isValidData(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy").withResolverStyle(ResolverStyle.SMART);

        try{
            LocalDate data = LocalDate.parse(this.getData(), dtf);

            if (data.isAfter(LocalDate.now()))
                return false;

        } catch (Exception e) {
            return false;
        }

        return true;
    }

     */
}
