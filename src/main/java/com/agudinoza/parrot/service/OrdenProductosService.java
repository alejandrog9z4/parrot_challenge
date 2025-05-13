package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.entity.OrdenProductos;
import com.agudinoza.parrot.repository.IOrdenProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrdenProductosService implements IOrdenProductosService {

    @Autowired
    private IOrdenProductosRepository ordenProductosRepository;

    @Override
    public List<OrdenProductos> findByOrdenId(UUID ordenId) {
        List<OrdenProductos> ordenProductos = ordenProductosRepository.findByOrdenId(ordenId);
        if (ordenProductos == null || ordenProductos.isEmpty()) {
            throw new RuntimeException("No se encontraron productos para la orden con ID: " + ordenId);
        }
        return ordenProductos;
    }

    @Override
    public List<OrdenProductos> createOrdenProductos(List<OrdenProductos> productos) {
        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("La lista de productos no puede ser nula o estar vacía");
        }

        try {
            List<OrdenProductos> savedProductos = ordenProductosRepository.saveAll(productos);
            return savedProductos;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar los productos: " + e.getMessage(), e);
        }
    }
}
