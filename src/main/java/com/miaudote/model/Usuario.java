package com.miaudote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

@Entity
@Table(name="Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String email; // regex

    @Transient //o campo 'senha' ficará somente na memória para validação, depois é transformada em hash
    private String senha; // regex aqui, autenticação/criptografia em Service
    private String senhaHash; // vai para o banco
    private int numEndereco;
    private String complEndereco;
    private String telefone; // regex
    private LocalDate dataCadastro;

    //vai gerar a data de cadastro pra agora automaticamente, antes de salvar no banco de dados
    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDate.now();
    }

    public boolean isValidUsuario(){
        return isValidNome() && isValidEmail() && isValidSenha()
                && isValidNumEndereco() && isValidComplEndereco() && isValidTelefone();
    }


    public boolean isValidNome(){
        try {
            String nome = getNome();
            if (nome == null || nome.trim().isEmpty())
                return false;

            if (nome.trim().length() < 2 || nome.trim().length() > 60)
                return false;

            // Apenas letras e espaços
            return nome.matches("^[\\p{L} ]+$");

        } catch (Exception e){
            return false;
        }
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
    public boolean isValidSenha(){
        final String PASSWORD_REGEX =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        String senha = getSenha();

        if (senha == null || senha.isEmpty())
            return false;

        return PASSWORD_PATTERN.matcher(senha).matches();

    }

    public boolean isValidNumEndereco(){
        try {
            if (getNumEndereco() <= 0)
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isValidComplEndereco(){
        try {
            if (getComplEndereco() == null || getComplEndereco().isEmpty())
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
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
