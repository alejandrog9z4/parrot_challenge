package com.agudinoza.parrot.controller;


import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.security.JwtTokenProvider;
import com.agudinoza.parrot.service.IMeseroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "API para registro e inicio de sesión de meseros")
public class AuthController {

    @Autowired
    private  IMeseroService meseroService;

    @Autowired
    private  JwtTokenProvider tokenProvider;


    @PostMapping("/register")
    @Operation(
        summary = "Registrar un nuevo mesero",
        description = "Registra un nuevo mesero en el sistema y devuelve un token JWT para autenticación"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mesero registrado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Map.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de registro inválidos",
            content = @Content
        )
    })
    public ResponseEntity<?> registerMesero(@RequestBody MeseroRequestDto meseroDto) {
        MeseroResponseDto mesero = meseroService.save(meseroDto);
        String token = tokenProvider.generateToken(mesero.getMail());

        Map<String, Object> response = new HashMap<>();
        response.put("mesero", mesero.getNombre());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica a un mesero existente y devuelve un token JWT para autenticación"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Inicio de sesión exitoso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Map.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Mesero no encontrado",
            content = @Content
        )
    })
    public ResponseEntity<?> loginMesero(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("mail");
            Mesero mesero = meseroService.findByEmail(email);
            String token = tokenProvider.generateToken(mesero.getMail());

            Map<String, Object> response = new HashMap<>();
            response.put("mesero", mesero.getNombre());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Mesero no encontrado"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error en el servidor: " + e.getMessage()));
        }
    }
}

