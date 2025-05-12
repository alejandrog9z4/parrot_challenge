package com.agudinoza.parrot.controller;

import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.security.JwtTokenProvider;
import com.agudinoza.parrot.service.IMeseroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IMeseroService meseroService;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;
    private MeseroRequestDto meseroRequestDto;
    private MeseroResponseDto meseroResponseDto;
    private Mesero mesero;
    private String token;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();

        // Configurar datos de prueba
        UUID meseroId = UUID.randomUUID();

        meseroRequestDto = new MeseroRequestDto();
        meseroRequestDto.setNombre("Test Mesero");
        meseroRequestDto.setMail("test@example.com");

        meseroResponseDto = new MeseroResponseDto();
        meseroResponseDto.setId(meseroId);
        meseroResponseDto.setNombre("Test Mesero");
        meseroResponseDto.setMail("test@example.com");

        mesero = new Mesero();
        mesero.setId(meseroId);
        mesero.setNombre("Test Mesero");
        mesero.setMail("test@example.com");

        token = "test-jwt-token";
    }

    @Test
    void registerMesero_ValidRequest_ReturnsOkWithToken() throws Exception {
        // Arrange
        when(meseroService.save(any(MeseroRequestDto.class))).thenReturn(meseroResponseDto);
        when(tokenProvider.generateToken(anyString())).thenReturn(token);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meseroRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mesero").value("Test Mesero"))
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void loginMesero_ValidCredentials_ReturnsOkWithToken() throws Exception {
        // Arrange
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("mail", "test@example.com");

        when(meseroService.findByEmail("test@example.com")).thenReturn(mesero);
        when(tokenProvider.generateToken(anyString())).thenReturn(token);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mesero").value("Test Mesero"))
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void loginMesero_MeseroNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("mail", "nonexistent@example.com");

        when(meseroService.findByEmail("nonexistent@example.com")).thenThrow(new RuntimeException("Mesero no encontrado"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }

}