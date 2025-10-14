package com.miaudote.model;

public class ValidacaoNome {

    public static boolean isValidNome(String nome){
        try {
            if (nome == null || nome.trim().isEmpty())
                return false;

            if (nome.trim().length() > 60) // Eu to aceitando nomes com 1 caractere td bem? Pra podermos usar para animais tb kkkk
                return false;

            // Apenas letras e espaços
            return nome.matches("^[\\p{L} ]+$");

        } catch (Exception e){
            return false;
        }
    }

}
