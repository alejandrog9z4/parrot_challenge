package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.ReporteProductoDto;
import com.agudinoza.parrot.model.dto.ReporteRequestDto;
import com.agudinoza.parrot.model.dto.ReporteResponseDto;
import com.agudinoza.parrot.model.entity.OrdenProductos;
import com.agudinoza.parrot.repository.IReporteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReporteService implements IReporteService {

    @Autowired
    private IReporteRepository reporteRepository;;

    @Override
    public ReporteResponseDto getReporte(ReporteRequestDto reporte ) {
        try {
            LocalDate inicio = reporte.getInicio();
            LocalDate fin = reporte.getFin();
            List<OrdenProductos> reporte1 = reporteRepository.findByFechaCreacionBetween(inicio, fin);

            Map<String, ReporteProductoDto> productosAgrupados = reporte1.stream()
                    .collect(Collectors.groupingBy(
                            OrdenProductos::getNombre,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    productos -> {
                                        int cantidadTotal = productos.stream()
                                                .mapToInt(OrdenProductos::getCantidad)
                                                .sum();
                                        double precioUnitario = productos.get(0).getPrecio_unitario();
                                        double subtotal = precioUnitario * cantidadTotal;

                                        return new ReporteProductoDto(
                                                productos.get(0).getNombre(),
                                                precioUnitario,
                                                cantidadTotal,
                                                subtotal
                                        );
                                    }
                            )
                    ));
            List<ReporteProductoDto> listaProductos = productosAgrupados.values().stream()
                    .sorted((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()))
                    .collect(Collectors.toList());

            double totalGeneral = listaProductos.stream()
                    .mapToDouble(ReporteProductoDto::getSubtotal)
                    .sum();


            return ReporteResponseDto.builder()
                    .fechaInicio(inicio)
                    .fechaFin(fin)
                    .productos(listaProductos)
                    .total(totalGeneral)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

