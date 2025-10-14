package com.miaudote.model;

public class ValidacaoNome {

    public static boolean isValidNome(String nome){
        try {
            if (nome == null || nome.trim().isEmpty())
                return false;

            if (nome.trim().length() < 2 && nome.trim().length() > 60) 
                return false;

            // Apenas letras e espaços
            return nome.matches("^[\\p{L} ]+$");

        } catch (Exception e){
            return false;
        }
    }

}
