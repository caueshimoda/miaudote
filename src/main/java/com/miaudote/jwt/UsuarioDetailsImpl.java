package com.miaudote.jwt;

import com.miaudote.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UsuarioDetailsImpl implements UserDetails {

    private final Long id;
    
    private final String email;
    private final String senhaHash;
    private final Collection<? extends GrantedAuthority> authorities;

    // Construtor privado para garantir que a classe só seja criada via método build()
    private UsuarioDetailsImpl(
        Long id, 
        String email, 
        String senhaHash, 
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.email = email;
        this.senhaHash = senhaHash;
        this.authorities = authorities;
    }

    // Cria o objeto UserDetailsImpl a partir da sua entidade Usuario
    public static UsuarioDetailsImpl build(Usuario usuario) {
        
        // Retorna uma lista vazia de autoridades porque não usamos roles/perfis
        List<GrantedAuthority> authorities = Collections.emptyList(); 

        return new UsuarioDetailsImpl(
            usuario.getId(), 
            usuario.getEmail(),
            usuario.getSenha_hash(), 
            authorities
        );
    }

    public Long getId() {
        return id;
    }

    // Métodos obrigatórios da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; 
    }

    // Método de comparação 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDetailsImpl that = (UsuarioDetailsImpl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
