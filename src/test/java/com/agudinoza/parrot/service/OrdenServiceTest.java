package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.OrdenProductosRequestDto;
import com.agudinoza.parrot.model.dto.OrdenProductosResponseDto;
import com.agudinoza.parrot.model.dto.OrdenRequestDto;
import com.agudinoza.parrot.model.dto.OrdenResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.model.entity.Orden;
import com.agudinoza.parrot.model.entity.OrdenProductos;
import com.agudinoza.parrot.repository.IOrderRepository;
import com.agudinoza.parrot.utils.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrdenProductosService ordenProductosService;

    @Mock
    private IMeseroService meseroService;

    @Mock
    private IReporteService reporteService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrdenService ordenService;

    private UUID ordenId;
    private Orden orden;
    private Mesero mesero;
    private List<OrdenProductos> ordenProductosList;
    private OrdenRequestDto ordenRequestDto;

    @BeforeEach
    void setUp() {

        ordenId = UUID.randomUUID();
        mesero = new Mesero();
        mesero.setId(UUID.randomUUID());
        mesero.setNombre("Test Mesero");
        mesero.setMail("test@example.com");

        ordenProductosList = new ArrayList<>();
        OrdenProductos ordenProducto = new OrdenProductos();
        ordenProductosList.add(ordenProducto);

        orden = Orden.builder()
                .id(ordenId)
                .nombre_cliente("Test Cliente")
                .fecha(LocalDate.now())
                .mesero(mesero)
                .productos(ordenProductosList)
                .total(100.0)
                .build();


        ordenRequestDto = new OrdenRequestDto();
        ordenRequestDto.setNombre("Test Cliente");
        List<OrdenProductosRequestDto> productoRequestDtos = new ArrayList<>();
        OrdenProductosRequestDto productoRequestDto = new OrdenProductosRequestDto();
        productoRequestDtos.add(productoRequestDto);
        ordenRequestDto.setProductos(productoRequestDtos);


        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findById_ExistingOrder_ReturnsOrderResponseDto() {

        when(orderRepository.findById(ordenId)).thenReturn(Optional.of(orden));

        List<OrdenProductosResponseDto> productosResponseDto = new ArrayList<>();
        try (MockedStatic<Helper> helperMockedStatic = Mockito.mockStatic(Helper.class)) {
            helperMockedStatic.when(() -> Helper.mapToOrdenProductosResponseDto(any()))
                    .thenReturn(productosResponseDto);


            OrdenResponseDto result = ordenService.findById(ordenId);


            assertNotNull(result);
            assertEquals(ordenId, result.getId());
            assertEquals("Test Cliente", result.getNombre());
            assertEquals("Test Mesero", result.getMesero());
            assertEquals(productosResponseDto, result.getProductos());
            assertEquals(100.0, result.getTotal());


            verify(orderRepository).findById(ordenId);
            helperMockedStatic.verify(() -> Helper.mapToOrdenProductosResponseDto(ordenProductosList));
        }
    }

    @Test
    void findById_NonExistingOrder_ThrowsRuntimeException() {

        when(orderRepository.findById(ordenId)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ordenService.findById(ordenId);
        });

        assertTrue(exception.getMessage().contains("Order not found"));
        verify(orderRepository).findById(ordenId);
    }

    @Test
    void findByMeseroId_ReturnsEmptyList() {

        List<OrdenRequestDto> result = ordenService.findByMeseroId(UUID.randomUUID());


        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}