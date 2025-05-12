package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.entity.OrdenProductos;

import java.time.LocalDate;
import java.util.List;


public interface IReporteService  {
    public List<OrdenProductos> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
