package com.agudinoza.parrot.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenProductosResponseDto {
    private String nombre;
    private Double precio_unitario;
    private Integer cantidad;
    private LocalDate fecha_creacion;
}
