package com.indichess.authservice.controller;

import com.indichess.authservice.model.User;
import com.indichess.authservice.model.dto.LoginDto;
import com.indichess.authservice.model.dto.LoginResponseDto;
import com.indichess.authservice.service.AuthService;
import com.indichess.authservice.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<User> handleSignup(@RequestBody User user) {
        return new ResponseEntity<>(authService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(HttpServletResponse response, @RequestBody LoginDto loginDto) {
        Authentication authObject = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        if (authObject.isAuthenticated()) {
            String token = jwtService.generateToken(loginDto.getUsername());
            ResponseCookie cookie = ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("lax")
                .path("/")
                .maxAge(3600)
                .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(token);
        }
        return new ResponseEntity<>(new LoginResponseDto(null, "Auth Failed"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> handleLogout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("JWT", "")
            .httpOnly(true)
            .secure(false)
            .sameSite("lax")
            .path("/")
                .maxAge(0)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logged out successfully");
    }
}
