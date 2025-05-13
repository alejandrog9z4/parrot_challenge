package com.agudinoza.parrot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ReporteRequestDto {
    LocalDate inicio;
    LocalDate fin;
}
