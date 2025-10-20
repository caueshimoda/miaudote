package com.miaudote.model;

import java.time.LocalDate;

public class Validacao {

    public static final int MAX_TEXTO = 255;

    public static final int MAX_NOME = 60;

    public static final int MIN_NOME = 2;

    public static boolean isValidNome(String nome){
        try {
            if (nome == null || nome.trim().isEmpty())
                return false;

            if (nome.trim().length() < MIN_NOME && nome.trim().length() > MAX_NOME) 
                return false;

            // Apenas letras e espaços
            return nome.matches("^[\\p{L} ]+$");

        } catch (Exception e){
            return false;
        }
    }

    public static boolean isValidData(LocalDate data) {

        return (!data.isAfter(LocalDate.now()));
        
    }

    public static boolean isValidCPF(String cpf) {

        if (cpf == null) {
            return false;
        }

        // Apenas dígitos, exatamente 11, e não podem ser 11 iguais (segundo regex checa isso)
        if (!cpf.matches("^\\d{11}$") || cpf.matches("(\\\\d)\\\\1{10}"))
            return false;

        // Primeiro dígito
        int soma = 0;
        int resto;

        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i)); 
            soma += digito * (10 - i);
        }

        resto = (soma * 10) % 11;

        // Se o resto for 10 ou 11, o dígito verificador é 0
        if (resto == 10 || resto == 11) {
            resto = 0;
        }
        
        // Compara com o décimo dígito, que é o primeiro verificador
        if (resto != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Segundo dígito
        soma = 0;
        
        for (int i = 0; i < 10; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma += digito * (11 - i);
        }

        resto = (soma * 10) % 11;
        
        if (resto == 10 || resto == 11) {
            resto = 0;
        }
        
        // Compara com o segundo verificador
        if (resto != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }

    public static boolean isValidCNPJ(String cnpj) {
        
        if (cnpj == null) {
            return false;
        }

        // Apenas dígitos, exatamente 14, e não podem ser 14 iguais (segundo regex checa isso)
        if (!cnpj.matches("^\\d{14}$") || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int tamanho = 12;
        String numeros = cnpj.substring(0, tamanho); 
        String digitos = cnpj.substring(tamanho);   
        int soma;
        int pos;
        int resultado;

        // Primeiro dígito
        soma = 0;
        pos = tamanho - 7; 

        for (int i = tamanho; i >= 1; i--) {
            // Pega o dígito da esquerda para a direita
            int digito = Character.getNumericValue(numeros.charAt(tamanho - i)); 
            
            soma += digito * pos--;
            
            if (pos < 2) {
                pos = 9; // Reinicia o peso para 9 quando chega a 1
            }
        }

        resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        // Compara com o primeiro dígito verificador
        if (resultado != Character.getNumericValue(digitos.charAt(0))) {
            return false;
        }

        // Segundo dígito
        tamanho = tamanho + 1; 
        numeros = cnpj.substring(0, tamanho); 
        soma = 0;
        pos = tamanho - 7;

        for (int i = tamanho; i >= 1; i--) {
            int digito = Character.getNumericValue(numeros.charAt(tamanho - i));
            
            soma += digito * pos--;
            
            if (pos < 2) {
                pos = 9;
            }
        }

        resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        // Compara com o segundo dígito verificador
        int digitoVerificador2 = Character.getNumericValue(digitos.charAt(1));
        if (resultado != digitoVerificador2) {
            return false;
        }

        return true;
    }

    public static boolean isValidText(String text) {
        if (text == null) 
            return true;
        
        return text.length() <= MAX_TEXTO;
    }

}
