package com.miaudote.jwt;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    public JwtResponse(String token) { this.token = token; }
    private Long id;

    public JwtResponse(String token, Long id) { 
        this.token = token;
        this.id = id; 
    }

}
