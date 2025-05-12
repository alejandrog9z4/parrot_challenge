package com.agudinoza.parrot.controller;

import com.agudinoza.parrot.model.dto.OrdenProductosRequestDto;
import com.agudinoza.parrot.model.dto.OrdenProductosResponseDto;
import com.agudinoza.parrot.model.dto.OrdenRequestDto;
import com.agudinoza.parrot.model.dto.OrdenResponseDto;
import com.agudinoza.parrot.service.IMeseroService;
import com.agudinoza.parrot.service.IOrdenService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrdenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IOrdenService ordenService;

    @Mock
    private IMeseroService meseroService;

    @InjectMocks
    private OrdenController ordenController;

    private ObjectMapper objectMapper;
    private UUID ordenId;
    private OrdenRequestDto ordenRequestDto;
    private OrdenResponseDto ordenResponseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ordenController).build();
        objectMapper = new ObjectMapper();

        // Configurar datos de prueba
        ordenId = UUID.randomUUID();

        // Configurar OrdenRequestDto
        ordenRequestDto = new OrdenRequestDto();
        ordenRequestDto.setNombre("Cliente Test");
        List<OrdenProductosRequestDto> productos = new ArrayList<>();
        OrdenProductosRequestDto producto = new OrdenProductosRequestDto();
        producto.setNombre("test_producto");
        producto.setPrecio_unitario(100.0);
        producto.setCantidad(2);
        productos.add(producto);
        ordenRequestDto.setProductos(productos);

        // Configurar OrdenResponseDto
        List<OrdenProductosResponseDto> productosResponse = new ArrayList<>();
        OrdenProductosResponseDto productoResponse = new OrdenProductosResponseDto();
        productoResponse.setNombre("Producto Test");
        productoResponse.setPrecio_unitario(100.0);
        productoResponse.setCantidad(2);
        productosResponse.add(productoResponse);

        ordenResponseDto = new OrdenResponseDto();
        ordenResponseDto.setId(ordenId);
        ordenResponseDto.setNombre("Cliente Test");
        ordenResponseDto.setMesero("Mesero Test");
        ordenResponseDto.setProductos(productosResponse);
        ordenResponseDto.setTotal(200.0);
    }

    @Test
    void getOrdenById_ExistingOrden_ReturnsOkWithOrden() throws Exception {
        // Arrange
        when(ordenService.findById(ordenId)).thenReturn(ordenResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/orden/" + ordenId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ordenId.toString()))
                .andExpect(jsonPath("$.nombre").value("Cliente Test"))
                .andExpect(jsonPath("$.mesero").value("Mesero Test"))
                .andExpect(jsonPath("$.productos[0].nombre").value("Producto Test"))
                .andExpect(jsonPath("$.total").value(200.0));
    }

    @Test
    void createOrden_ValidOrden_ReturnsCreatedWithId() throws Exception {
        // Arrange
        when(ordenService.createOrden(any(OrdenRequestDto.class))).thenReturn(ordenId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/orden")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordenRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(ordenId.toString()));
    }

}