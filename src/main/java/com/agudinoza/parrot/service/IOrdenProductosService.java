package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IOrdenProductosService  {
    List<OrdenProductos> findByOrdenId(UUID ordenId);
    List<OrdenProductos> createOrdenProductos(List<OrdenProductos> productos);
}
