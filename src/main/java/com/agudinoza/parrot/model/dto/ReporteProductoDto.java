package com.agudinoza.parrot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteProductoDto {
    private String nombre;
    private Double precioUnitario;
    private Integer cantidad;
    private Double subtotal;
}
