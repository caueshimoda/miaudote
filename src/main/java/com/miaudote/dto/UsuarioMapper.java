package com.miaudote.dto;

// gente, eu juro que essa anotação existe, o IntelliJ que não ta aceitando minha dependência
// Tudo certo Bruna, só faltou importar o Mapper KKKK

import org.mapstruct.Mapper;
import com.miaudote.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioCadastroDTO dto);
    UsuarioDTO toDTO(Usuario usuario);

    /* Segundo Copilot:
    O MapStruct é uma biblioteca Java que gera automaticamente o código de mapeamento entre classes — como entre DTOs e entidades — em tempo de compilação.
    Você define uma interface com métodos de mapeamento, e o MapStruct gera uma implementação concreta com o código necessário para copiar os dados entre os objetos.

    */
}
