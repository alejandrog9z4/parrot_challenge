package com.agudinoza.parrot.controller;


import com.agudinoza.parrot.model.dto.OrdenRequestDto;
import com.agudinoza.parrot.model.dto.OrdenResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.service.IMeseroService;
import com.agudinoza.parrot.service.IOrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/orden")
@Tag(name = "Órdenes", description = "API para gestión de órdenes de restaurante")
@SecurityRequirement(name = "bearerAuth")
public class OrdenController {

    @Autowired
    private  IOrdenService ordenService;

    @Autowired
    private  IMeseroService meseroService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener orden por ID",
        description = "Recupera los detalles de una orden específica según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orden encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OrdenResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado para acceder a este recurso",
            content = @Content
        )
    })
    public ResponseEntity<OrdenResponseDto> getOrdenById(@PathVariable UUID id){
        OrdenResponseDto orden = ordenService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orden);
    }

    @PostMapping("")
    @Operation(
        summary = "Crear nueva orden",
        description = "Registra una nueva orden en el sistema con los productos seleccionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Orden creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UUID.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos para crear la orden",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado para acceder a este recurso",
            content = @Content
        )
    })
    public ResponseEntity<UUID> createOrden(@RequestBody OrdenRequestDto ordenRequestDto){
        UUID id_orden = ordenService.createOrden(ordenRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id_orden);
    }


}
