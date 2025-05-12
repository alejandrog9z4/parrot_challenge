package com.agudinoza.parrot.controller;

import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.service.MeseroService;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MeseroControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MeseroService meseroService;

    @InjectMocks
    private MeseroController meseroController;

    private ObjectMapper objectMapper;
    private UUID meseroId;
    private MeseroRequestDto meseroRequestDto;
    private MeseroResponseDto meseroResponseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(meseroController).build();
        objectMapper = new ObjectMapper();

        // Configurar datos de prueba
        meseroId = UUID.randomUUID();

        meseroRequestDto = new MeseroRequestDto();
        meseroRequestDto.setNombre("Test Mesero");
        meseroRequestDto.setMail("test@example.com");

        meseroResponseDto = new MeseroResponseDto();
        meseroResponseDto.setId(meseroId);
        meseroResponseDto.setNombre("Test Mesero");
        meseroResponseDto.setMail("test@example.com");
    }

    @Test
    void findById_ExistingMesero_ReturnsOkWithMesero() throws Exception {
        // Arrange
        when(meseroService.findById(meseroId)).thenReturn(meseroResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/mesero/" + meseroId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(meseroId.toString()))
                .andExpect(jsonPath("$.nombre").value("Test Mesero"))
                .andExpect(jsonPath("$.mail").value("test@example.com"));
    }

    @Test
    void save_ValidMesero_ReturnsCreated() throws Exception {
        // Arrange
        when(meseroService.save(any(MeseroRequestDto.class))).thenReturn(meseroResponseDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/mesero")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meseroRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void findByNombre_ExistingMesero_ReturnsOkWithMesero() throws Exception {
        // Arrange
        String nombre = "Test Mesero";
        when(meseroService.findByNombre(nombre)).thenReturn(meseroResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/mesero/nombre/" + nombre)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(meseroId.toString()))
                .andExpect(jsonPath("$.nombre").value("Test Mesero"))
                .andExpect(jsonPath("$.mail").value("test@example.com"));
    }


}