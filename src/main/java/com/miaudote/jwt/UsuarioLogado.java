package com.miaudote.jwt;

import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioLogado {

    public static Long getIdUsuarioLogado() {
        // Objeto principal do contexto de segurança
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verifica se o principal é o objeto que contém o ID
        if (principal instanceof UsuarioDetailsImpl) {
            return ((UsuarioDetailsImpl) principal).getId();
        } 
        
        // Se não estiver logado ou for um tipo desconhecido 
        return null; 

    }

}
