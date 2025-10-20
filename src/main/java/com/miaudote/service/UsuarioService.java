package com.miaudote.service;

import com.miaudote.dto.UsuarioCadastroDTO;
import com.miaudote.dto.UsuarioMapper;
import com.miaudote.model.Usuario;
import com.miaudote.model.Validacao;
import com.miaudote.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    // Welcome back Bruna, your dedication to the Miaudote project has been exceptional.
    // You've taken ownership of the backend building process and established yourself as the tech lead,
    // which is a great asset to the team. Keep up the excellent work!


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }


    public Usuario cadastrarUsuario(UsuarioCadastroDTO dto){
        Usuario usuario;

        usuario = usuarioMapper.toEntity(dto);

        if (!usuario.isValidSenha(dto.getSenha())) 
            throw new IllegalArgumentException("Senha inválida! Ela deve ter ao menos 8 caracteres, com maiúscula, minúscula, número e caractere especial.");
        
        if (!usuario.isValidNome())
            throw new IllegalArgumentException(String.format("Nome do usuário inválido, deve ter apenas letras e espaços, entre %d e %d caracteres.", Validacao.MIN_NOME, Validacao.MAX_NOME));

        if (!usuario.isValidCidade())
            throw new IllegalArgumentException(String.format("Cidade do usuário inválida, deve ter apenas letras e espaços, entre %d e %d caracteres.", Validacao.MIN_NOME, Validacao.MAX_NOME));

        if (!usuario.isValidEmail())
            throw new IllegalArgumentException("Email do Usuário inválido.");

        if (!usuario.isValidEstado())
            throw new IllegalArgumentException("Estado do Usuário inválido.");

        if (!usuario.isValidTelefone())
            throw new IllegalArgumentException("Telefone do Usuário inválido.");
            
         // seta a 'senhaHash' com a criptografia de 'senha'
        usuario.setSenha_hash(passwordEncoder.encode(dto.getSenha()));
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

    public Usuario atualizarUsuario(Long id, UsuarioCadastroDTO novosDados){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //todo: criar método separado para o merge de dados novos com os existentes
        Optional.ofNullable(novosDados.getNome()).ifPresent(usuarioExistente::setNome);
        Optional.ofNullable(novosDados.getEmail()).ifPresent(usuarioExistente::setEmail);
        Optional.ofNullable(novosDados.getCidade()).ifPresent(usuarioExistente::setCidade);
        Optional.ofNullable(novosDados.getEstado()).ifPresent(usuarioExistente::setEstado);
        Optional.ofNullable(novosDados.getTelefone()).ifPresent(usuarioExistente::setTelefone);

        // atualizar a senha é opcional, mas se uma nova senha for informada:
        if(novosDados.getSenha() != null && !novosDados.getSenha().isEmpty()){
            if (!usuarioExistente.isValidSenha(novosDados.getSenha()))
                throw new IllegalArgumentException("Senha inválida");
            
            usuarioExistente.setSenha_hash(passwordEncoder.encode(novosDados.getSenha()));
            
        }

        // Você acha que não vale jogar erros se houver problema, igual no cadastro? Deixei assim, se quiser pode voltar como tava.
        if (!usuarioExistente.isValidCidade()) 
            throw new IllegalArgumentException("Cidade inválida");
        if (!usuarioExistente.isValidEmail()) 
            throw new IllegalArgumentException("E-mail inválido");
        if (!usuarioExistente.isValidEstado()) 
            throw new IllegalArgumentException("Estado inválido");
        if (!usuarioExistente.isValidNome()) 
            throw new IllegalArgumentException("Nome inválido");
        if (!usuarioExistente.isValidTelefone()) 
            throw new IllegalArgumentException("Telefone inválido");

        // validação dos novos dados
        //if(usuarioExistente.isValidUsuario())
            //usuarioRepository.save(usuarioExistente);

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
        
        /* Ms Ogura, tomei a liberdade de tirar esse try catch porque vamos fazer essa verificação em Parceiro e Adotante,
        veja se concorda.
        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir: usuário vinculado a outros registros", e);
        }
        */
    }

    public Usuario buscarPorEmail(String email) {
        /* esse metodo será utilizado depois que o user ja tiver criado a conta, então o e-mail já passou pela validação inicial,
            então não senti a necessidade de validar dnv. Aberta a discussão... ou não
        */

        return usuarioRepository.findByEmail(email)
                .orElse(null);
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
        Optional.of(oldUsuario.getCidade()).ifPresent(updatedUsuario::setCidade);
        Optional.ofNullable(oldUsuario.getEstado()).ifPresent(updatedUsuario::setEstado);
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
