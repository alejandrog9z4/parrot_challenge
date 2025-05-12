package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReporteService implements IReporteService {
    @Override
    public List<OrdenProductos> findByFechaBetween(LocalDate inicio, LocalDate fin) {
        return null;
    }
}
