package com.miaudote.jwt;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    public JwtResponse(String token) { this.token = token; }
    private Long id;
    private String tipo;

    public JwtResponse(String token, Long id, String tipo) { 
        this.token = token;
        this.id = id; 
        this.tipo = tipo;
    }

}
