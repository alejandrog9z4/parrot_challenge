package com.agudinoza.parrot.model.dto;

import com.agudinoza.parrot.model.entity.Mesero;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenRequestDto {
    private String nombre;
    private Mesero mesero;
    List<OrdenProductosRequestDto> productos;
}
