package com.agudinoza.parrot.utils;

import com.agudinoza.parrot.model.dto.OrdenProductosRequestDto;
import com.agudinoza.parrot.model.dto.OrdenProductosResponseDto;
import com.agudinoza.parrot.model.entity.Orden;
import com.agudinoza.parrot.model.entity.OrdenProductos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HelperTest {

    @Test
    void calcularTotal_EmptyList_ReturnsZero() {
        // Arrange
        List<OrdenProductos> productos = new ArrayList<>();

        // Act
        double result = Helper.calcularTotal(productos);

        // Assert
        assertEquals(0.0, result, 0.001);
    }

    @Test
    void calcularTotal_SingleProduct_ReturnsCorrectTotal() {
        // Arrange
        List<OrdenProductos> productos = new ArrayList<>();
        OrdenProductos producto = OrdenProductos.builder()
                .precio_unitario(100.0)
                .cantidad(2)
                .build();
        productos.add(producto);

        // Act
        double result = Helper.calcularTotal(productos);

        // Assert
        assertEquals(200.0, result, 0.001);
    }

    @Test
    void calcularTotal_MultipleProducts_ReturnsCorrectTotal() {
        // Arrange
        List<OrdenProductos> productos = new ArrayList<>();
        OrdenProductos producto1 = OrdenProductos.builder()
                .precio_unitario(100.0)
                .cantidad(2)
                .build();
        OrdenProductos producto2 = OrdenProductos.builder()
                .precio_unitario(50.0)
                .cantidad(3)
                .build();
        productos.add(producto1);
        productos.add(producto2);

        // Act
        double result = Helper.calcularTotal(productos);

        // Assert
        assertEquals(350.0, result, 0.001); // (100*2) + (50*3) = 200 + 150 = 350
    }

    @Test
    void mapToOrdenProductos_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<OrdenProductosRequestDto> productosDto = new ArrayList<>();
        Orden orden = Orden.builder().id(UUID.randomUUID()).build();

        // Act
        List<OrdenProductos> result = Helper.mapToOrdenProductos(productosDto, orden);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void mapToOrdenProductos_ValidList_ReturnsCorrectMapping() {
        // Arrange
        List<OrdenProductosRequestDto> productosDto = new ArrayList<>();
        OrdenProductosRequestDto productoDto1 = OrdenProductosRequestDto.builder()
                .nombre("Producto 1")
                .precio_unitario(100.0)
                .cantidad(2)
                .build();
        OrdenProductosRequestDto productoDto2 = OrdenProductosRequestDto.builder()
                .nombre("Producto 2")
                .precio_unitario(50.0)
                .cantidad(3)
                .build();
        productosDto.add(productoDto1);
        productosDto.add(productoDto2);

        Orden orden = Orden.builder().id(UUID.randomUUID()).build();

        // Act
        List<OrdenProductos> result = Helper.mapToOrdenProductos(productosDto, orden);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar primer producto
        assertEquals("Producto 1", result.get(0).getNombre());
        assertEquals(100.0, result.get(0).getPrecio_unitario(), 0.001);
        assertEquals(2, result.get(0).getCantidad());
        assertEquals(orden, result.get(0).getOrden());

        // Verificar segundo producto
        assertEquals("Producto 2", result.get(1).getNombre());
        assertEquals(50.0, result.get(1).getPrecio_unitario(), 0.001);
        assertEquals(3, result.get(1).getCantidad());
        assertEquals(orden, result.get(1).getOrden());
    }

    @Test
    void mapToOrdenProductosResponseDto_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<OrdenProductos> productos = new ArrayList<>();

        // Act
        List<OrdenProductosResponseDto> result = Helper.mapToOrdenProductosResponseDto(productos);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void mapToOrdenProductosResponseDto_ValidList_ReturnsCorrectMapping() {
        // Arrange
        List<OrdenProductos> productos = new ArrayList<>();
        OrdenProductos producto1 = OrdenProductos.builder()
                .nombre("Producto 1")
                .precio_unitario(100.0)
                .cantidad(2)
                .build();
        OrdenProductos producto2 = OrdenProductos.builder()
                .nombre("Producto 2")
                .precio_unitario(50.0)
                .cantidad(3)
                .build();
        productos.add(producto1);
        productos.add(producto2);

        // Act
        List<OrdenProductosResponseDto> result = Helper.mapToOrdenProductosResponseDto(productos);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar primer producto
        assertEquals("Producto 1", result.get(0).getNombre());
        assertEquals(100.0, result.get(0).getPrecio_unitario(), 0.001);
        assertEquals(2, result.get(0).getCantidad());

        // Verificar segundo producto
        assertEquals("Producto 2", result.get(1).getNombre());
        assertEquals(50.0, result.get(1).getPrecio_unitario(), 0.001);
        assertEquals(3, result.get(1).getCantidad());
    }
}