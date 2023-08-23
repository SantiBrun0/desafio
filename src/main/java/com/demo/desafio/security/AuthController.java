package com.demo.desafio.security;

import com.demo.desafio.excepciones.ErrorResponse;
import com.demo.desafio.usuario.Usuario;
import com.demo.desafio.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Optional<Usuario> usuario = usuarioService.buscarUsuario(request.getUsuario());
        if (usuario.isPresent()) {
            ErrorResponse errorResponse = new ErrorResponse("El usuario ya existe en la base de datos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.ok(authService.register(request));
    }

}
