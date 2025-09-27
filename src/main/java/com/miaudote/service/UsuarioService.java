package com.miaudote.service;

import com.miaudote.model.Usuario;
import com.miaudote.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    //todo: adicionar classes para exceções customizadas

    //todo: adicionar classe DTO

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Usuario cadastrarUsuario(Usuario usuario){
        if (!usuario.isValidSenha()) {
            throw new IllegalArgumentException("Senha inválida! Ela deve ter ao menos 8 caracteres, com maiúscula, minúscula, número e caractere especial.");
        }
        // seta a 'senhaHash' com a criptografia de 'senha'
        usuario.setSenha_hash(passwordEncoder.encode(usuario.getSenha()));
        usuario.setSenha(null); // seta a senha de texto puro como nula
        return usuarioRepository.save(usuario);
    }


    public Usuario loginUsuario(String email, String senhaDigitada){
        // busca usuário pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaDigitada, usuario.getSenha_hash())) {
            throw new RuntimeException("Senha inválida");
        }

        return usuario;
    }

    public Usuario atualizarUsuario(Long id, Usuario novosDados){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //todo: criar método separado para o merge de dados novos com os existentes
        Optional.ofNullable(novosDados.getNome()).ifPresent(usuarioExistente::setNome);
        Optional.ofNullable(novosDados.getEmail()).ifPresent(usuarioExistente::setEmail);
        Optional.of(novosDados.getNumero()).ifPresent(usuarioExistente::setNumero);
        Optional.ofNullable(novosDados.getComplemento()).ifPresent(usuarioExistente::setComplemento);
        Optional.ofNullable(novosDados.getTelefone()).ifPresent(usuarioExistente::setTelefone);
        Optional.ofNullable(novosDados.getStatus_usr()).ifPresent(usuarioExistente::setStatus_usr);

        // atualizar a senha é opcional, mas se uma nova senha for informada:
        if(novosDados.getSenha() != null && !novosDados.getSenha().isEmpty()){
            if (!novosDados.isValidSenha())
                throw new IllegalArgumentException("Senha inválida");
            
            usuarioExistente.setSenha_hash(passwordEncoder.encode(novosDados.getSenha()));
            
        }

        // validação dos novos dados
        if(usuarioExistente.isValidUsuario())
            usuarioRepository.save(usuarioExistente);

        return usuarioExistente;
    }

    public void deletarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: usuário vinculado a outros registros", e);
        }
    }




    // OS SEGUINTES MÉTODOS SÃO APENAS PARA TESTAR CONECTIVIDADE COM O BANCO, USANDO POSTMAN, NÃO SERÃO USADOS NA APLICAÇÃO REAL -----------------------

    // Read
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    // Filtro por id
    public Optional<Usuario> getUsuarioById(Long id){
        //só retorna se achar um com o id especificado
        return usuarioRepository.findById(id);
    }

    // Create
    public Usuario addUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    // Update
    public Usuario updateUsuario(Long id, Usuario oldUsuario){
        Optional<Usuario> usuarioData = usuarioRepository.findById(id);

        if (usuarioData.isEmpty()){
            return null;
        }

        Usuario updatedUsuario = usuarioData.get();

        Optional.ofNullable(oldUsuario.getNome()).ifPresent(updatedUsuario::setNome);
        Optional.ofNullable(oldUsuario.getEmail()).ifPresent(updatedUsuario::setEmail);
        Optional.ofNullable(oldUsuario.getSenha()).ifPresent(updatedUsuario::setSenha);
        Optional.of(oldUsuario.getNumero()).ifPresent(updatedUsuario::setNumero);
        Optional.ofNullable(oldUsuario.getComplemento()).ifPresent(updatedUsuario::setComplemento);
        Optional.ofNullable(oldUsuario.getTelefone()).ifPresent(updatedUsuario::setTelefone);
        

        if (updatedUsuario.isValidUsuario()){
            usuarioRepository.save(updatedUsuario);
        }

        return updatedUsuario;
    }

    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

}
