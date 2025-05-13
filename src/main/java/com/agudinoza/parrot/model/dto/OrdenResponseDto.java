package com.agudinoza.parrot.model.dto;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenResponseDto {
    private UUID id;
    private String nombre;
    private String mesero;
    private LocalDate fecha_creacion;
    private List<OrdenProductosResponseDto> productos;
    private Double total;
}
