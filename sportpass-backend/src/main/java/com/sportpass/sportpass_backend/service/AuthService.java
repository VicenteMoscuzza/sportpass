package com.sportpass.sportpass_backend.service;

import com.sportpass.sportpass_backend.dto.AuthDTO;
import com.sportpass.sportpass_backend.model.Usuario;
import com.sportpass.sportpass_backend.repository.UsuarioRepository;
import com.sportpass.sportpass_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthDTO.AuthResponse register(AuthDTO.RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setRol(Usuario.Rol.USER);

        usuarioRepository.save(usuario);

        emailService.enviarBienvenida(usuario.getEmail(), usuario.getNombre());

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthDTO.AuthResponse(token, usuario.getEmail(), usuario.getNombre(), usuario.getRol().name());
    }

    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthDTO.AuthResponse(token, usuario.getEmail(), usuario.getNombre(), usuario.getRol().name());
    }

    @Transactional(readOnly = true)
    public List<AuthDTO.UsuarioAdminResumen> listarUsuariosAdmin() {
        return usuarioRepository.findAll(Sort.by("email")).stream()
                .map(this::toUsuarioAdminResumen)
                .toList();
    }

    private AuthDTO.UsuarioAdminResumen toUsuarioAdminResumen(Usuario u) {
        AuthDTO.UsuarioAdminResumen dto = new AuthDTO.UsuarioAdminResumen();
        dto.setId(u.getId());
        dto.setEmail(u.getEmail());
        dto.setNombre(u.getNombre());
        dto.setRol(u.getRol().name());
        return dto;
    }
}