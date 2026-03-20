package com.sportpass.sportpass_backend.controller;

import com.sportpass.sportpass_backend.dto.AuthDTO;
import com.sportpass.sportpass_backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthDTO.UserInfoResponse> register(
            @RequestBody AuthDTO.RegisterRequest request,
            HttpServletResponse response) {
        AuthDTO.AuthResponse auth = authService.register(request);
        setJwtCookie(response, auth.getToken());
        return ResponseEntity.ok(new AuthDTO.UserInfoResponse(auth.getEmail(), auth.getNombre(), auth.getRol()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.UserInfoResponse> login(
            @RequestBody AuthDTO.LoginRequest request,
            HttpServletResponse response) {
        AuthDTO.AuthResponse auth = authService.login(request);
        setJwtCookie(response, auth.getToken());
        return ResponseEntity.ok(new AuthDTO.UserInfoResponse(auth.getEmail(), auth.getNombre(), auth.getRol()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);
    }
}