package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.ReporteRequestDto;
import com.agudinoza.parrot.model.dto.ReporteResponseDto;
import com.agudinoza.parrot.model.entity.OrdenProductos;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface IReporteService  {
    public ReporteResponseDto getReporte(ReporteRequestDto reporte);
}
