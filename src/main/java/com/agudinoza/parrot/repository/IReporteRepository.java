package com.agudinoza.parrot.repository;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IReporteRepository extends JpaRepository<OrdenProductos, UUID> {

    List<OrdenProductos> findByFechaCreacionBetween(LocalDate inicio,LocalDate fin);
}
