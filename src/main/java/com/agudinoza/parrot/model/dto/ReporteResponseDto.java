package com.agudinoza.parrot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponseDto {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<ReporteProductoDto> productos;
    private Double total;
}
