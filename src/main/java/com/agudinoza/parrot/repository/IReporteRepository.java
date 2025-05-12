package com.agudinoza.parrot.repository;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReporteRepository extends JpaRepository<OrdenProductos, UUID> {
}
