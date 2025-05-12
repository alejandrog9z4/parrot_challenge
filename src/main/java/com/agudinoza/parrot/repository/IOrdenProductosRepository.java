package com.agudinoza.parrot.repository;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrdenProductosRepository extends JpaRepository<OrdenProductos, UUID> {
    List<OrdenProductos> findByOrdenId(UUID ordenId);
}
