package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.model.dto.OrdenProductosResponseDto;
import com.agudinoza.parrot.model.dto.OrdenRequestDto;
import com.agudinoza.parrot.model.dto.OrdenResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.model.entity.Orden;
import com.agudinoza.parrot.model.entity.OrdenProductos;
import com.agudinoza.parrot.repository.IOrderRepository;
import com.agudinoza.parrot.utils.Helper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdenService implements IOrdenService {

   @Autowired
    private  IOrderRepository orderRepository;
    @Autowired
    private  IOrdenProductosService ordenProductosService;
    @Autowired
    private  IMeseroService meseroService;
    @Autowired
    private  IReporteService reporteService;

    @Override
    public OrdenResponseDto findById(UUID id) {
        try {
            Optional<Orden> order = orderRepository.findById(id);
            if (!order.isPresent()) {
                throw new RuntimeException("Order not found");
            }
            Orden orden = order.get();
            Mesero mesero = orden.getMesero();
            List<OrdenProductosResponseDto> productos = Helper.mapToOrdenProductosResponseDto(orden.getProductos());
            OrdenResponseDto ordenResponseDto = OrdenResponseDto.builder()
                    .id(orden.getId())
                    .nombre(orden.getNombre_cliente())
                    .mesero(mesero.getNombre())
                    .productos(productos)
                    .total(orden.getTotal())
                    .build();
            return ordenResponseDto;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar la orden: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public UUID createOrden(OrdenRequestDto ordenRequestDto) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Mesero mesero = meseroService.findByEmail(email);

            Orden orden = Orden.builder()
                    .nombre_cliente(ordenRequestDto.getNombre())
                    .fecha(LocalDate.now())
                    .mesero(mesero)
                    .productos(new ArrayList<>())
                    .build();
            Orden savedOrden = orderRepository.save(orden);

            List<OrdenProductos> ordenProductos = Helper.mapToOrdenProductos(ordenRequestDto.getProductos(), orden);
            List<OrdenProductos> savedOrdenProductos = ordenProductosService.createOrdenProductos(ordenProductos);
            savedOrden.setProductos(savedOrdenProductos);
            savedOrden.setTotal(Helper.calcularTotal(savedOrdenProductos));
            orderRepository.save(savedOrden);

            return savedOrden.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrdenRequestDto> findByMeseroId(UUID meseroId) {
        return List.of();
    }


}
