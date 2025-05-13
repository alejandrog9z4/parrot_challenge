package com.agudinoza.parrot.controller;


import com.agudinoza.parrot.model.dto.ReporteRequestDto;
import com.agudinoza.parrot.model.dto.ReporteResponseDto;
import com.agudinoza.parrot.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/v1/reporte")
public class ReporteController {

    @Autowired
    private IReporteService reporteService;

    @GetMapping("")
    public ResponseEntity<ReporteResponseDto> getReporte(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fin) {
        ReporteRequestDto request = new ReporteRequestDto(inicio, fin);

       reporteService.getReporte(request);

        return null;
    }
}
