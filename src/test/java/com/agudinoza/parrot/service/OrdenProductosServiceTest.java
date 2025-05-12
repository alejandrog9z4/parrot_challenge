package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.model.entity.Orden;
import com.agudinoza.parrot.model.entity.OrdenProductos;
import com.agudinoza.parrot.repository.IOrdenProductosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdenProductosServiceTest {

    @Mock
    private IOrdenProductosRepository ordenProductosRepository;

    @InjectMocks
    private OrdenProductosService ordenProductosService;

    private UUID ordenId;
    private List<OrdenProductos> ordenProductosList;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        Mesero mesero = Mesero.builder()
                .id(UUID.randomUUID())
                .nombre("Test Mesero")
                .mail("test@example.com")
                .build();
        Orden orden = Orden.builder()
                .id(UUID.randomUUID())
                .productos(new ArrayList<>())
                .total(0.0)
                .fecha(LocalDate.now())
                .nombre_cliente("test1")
                .mesero(mesero)
                .build();

        ordenProductosList = new ArrayList<>();
        OrdenProductos producto1 = new OrdenProductos();
        producto1.setId(1L);
        producto1.setOrden(orden);
        producto1.setNombre("Producto 1");
        producto1.setPrecio_unitario(100.0);
        producto1.setCantidad(2);

        OrdenProductos producto2 = new OrdenProductos();
        producto2.setId(2L);
        producto2.setOrden(orden);
        producto2.setNombre("Producto 2");
        producto2.setPrecio_unitario(150.0);
        producto2.setCantidad(1);

        ordenProductosList.add(producto1);
        ordenProductosList.add(producto2);
    }

    @Test
    void findByOrdenId_ExistingOrden_ReturnsProductosList() {
        // Arrange
        when(ordenProductosRepository.findByOrdenId(ordenId)).thenReturn(ordenProductosList);

        // Act
        List<OrdenProductos> result = ordenProductosService.findByOrdenId(ordenId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Producto 1", result.get(0).getNombre());
        assertEquals("Producto 2", result.get(1).getNombre());

        // Verify
        verify(ordenProductosRepository).findByOrdenId(ordenId);
    }

    @Test
    void findByOrdenId_EmptyList_ThrowsRuntimeException() {
        // Arrange
        when(ordenProductosRepository.findByOrdenId(ordenId)).thenReturn(new ArrayList<>());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ordenProductosService.findByOrdenId(ordenId);
        });

        assertEquals("No se encontraron productos para la orden con ID: " + ordenId, exception.getMessage());

        // Verify
        verify(ordenProductosRepository).findByOrdenId(ordenId);
    }

    @Test
    void findByOrdenId_NullList_ThrowsRuntimeException() {
        // Arrange
        when(ordenProductosRepository.findByOrdenId(ordenId)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ordenProductosService.findByOrdenId(ordenId);
        });

        assertEquals("No se encontraron productos para la orden con ID: " + ordenId, exception.getMessage());

        // Verify
        verify(ordenProductosRepository).findByOrdenId(ordenId);
    }

    @Test
    void createOrdenProductos_ValidList_ReturnsProductosList() {
        // Arrange
        when(ordenProductosRepository.saveAll(ordenProductosList)).thenReturn(ordenProductosList);

        // Act
        List<OrdenProductos> result = ordenProductosService.createOrdenProductos(ordenProductosList);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Producto 1", result.get(0).getNombre());
        assertEquals("Producto 2", result.get(1).getNombre());

        // Verify
        verify(ordenProductosRepository).saveAll(ordenProductosList);
    }

    @Test
    void createOrdenProductos_EmptyList_ThrowsIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ordenProductosService.createOrdenProductos(new ArrayList<>());
        });

        assertEquals("La lista de productos no puede ser nula o estar vacía", exception.getMessage());

        // Verify
        verify(ordenProductosRepository, never()).saveAll(any());
    }

    @Test
    void createOrdenProductos_NullList_ThrowsIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ordenProductosService.createOrdenProductos(null);
        });

        assertEquals("La lista de productos no puede ser nula o estar vacía", exception.getMessage());

        // Verify
        verify(ordenProductosRepository, never()).saveAll(any());
    }
}