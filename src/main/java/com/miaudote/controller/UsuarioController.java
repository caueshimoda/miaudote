package com.miaudote.controller;

import com.miaudote.dto.UsuarioLoginDTO;
import com.miaudote.dto.UsuarioMapper;
import com.miaudote.jwt.JwtResponse;
import com.miaudote.jwt.JwtUtil;
import com.miaudote.model.Usuario;
import com.miaudote.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    JwtUtil jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario loginRequest){
        try{
            UsuarioLoginDTO usuario = usuarioService.loginUsuario(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
            );

            
            String token = jwtUtils.generateToken(usuario.getEmail());

            // Mandamos de volta o token e o id do usuário
            return new ResponseEntity<JwtResponse>(new JwtResponse(token, usuario.getId(), usuario.getTipo()), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro ao efetuar login: " + e.getMessage());
        }
    }






    // OS SEGUINTES MÉTODOS SÃO APENAS PARA TESTAR CONECTIVIDADE COM O BANCO, USANDO POSTMAN, NÃO SERÃO USADOS NA APLICAÇÃO REAL -----------------------

    @GetMapping("/teste")
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        try {
            List<Usuario> usuarios = usuarioService.getUsuarios();

            if (usuarios.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(usuarios, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/teste/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping("/teste")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario){
        if(!usuario.isValidUsuario())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(usuarioService.addUsuario(usuario), HttpStatus.OK);
    }

    @PatchMapping("teste/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        try {
            if(!usuario.isValidUsuario())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);

            if(updatedUsuario == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/teste/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable Long id){
        try{
            usuarioService.deleteUsuario(id);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
