package com.agudinoza.parrot.utils;

import com.agudinoza.parrot.model.dto.OrdenProductosRequestDto;
import com.agudinoza.parrot.model.dto.OrdenProductosResponseDto;
import com.agudinoza.parrot.model.entity.Orden;
import com.agudinoza.parrot.model.entity.OrdenProductos;

import java.util.List;

public class Helper {

    public static double calcularTotal(List<OrdenProductos> productos) {
        double total = 0;
        for (OrdenProductos producto : productos) {
            total += producto.getPrecio_unitario() * producto.getCantidad();
        }
        return total;
    }

    public static List<OrdenProductos> mapToOrdenProductos(List<OrdenProductosRequestDto> productos, Orden orden) {
        return productos.stream()
                .map(producto -> OrdenProductos.builder()
                        .nombre(producto.getNombre())
                        .precio_unitario(producto.getPrecio_unitario())
                        .cantidad(producto.getCantidad())
                        .orden(orden)
                        .build())
                .toList();
    }

    public static List<OrdenProductosResponseDto> mapToOrdenProductosResponseDto(List<OrdenProductos> productos) {
        return productos.stream()
                .map(producto -> OrdenProductosResponseDto.builder()
                        .nombre(producto.getNombre())
                        .precio_unitario(producto.getPrecio_unitario())
                        .cantidad(producto.getCantidad())
                        .build())
                .toList();
    }
}
