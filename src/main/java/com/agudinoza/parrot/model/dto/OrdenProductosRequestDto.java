package com.agudinoza.parrot.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenProductosRequestDto {
    private String nombre;
    private Double precio_unitario;
    private Integer cantidad;
}
