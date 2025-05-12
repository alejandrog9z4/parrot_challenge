package com.agudinoza.parrot.controller;

import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.service.MeseroService;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/mesero")
@Tag(name = "Meseros", description = "API para gestión de meseros")
@SecurityRequirement(name = "bearerAuth")
public class MeseroController {

    @Autowired
    private  MeseroService meseroService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar mesero por ID",
        description = "Obtiene la información de un mesero específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mesero encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MeseroResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Mesero no encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado para acceder a este recurso",
            content = @Content
        )
    })
    public ResponseEntity<MeseroResponseDto> findById(@PathVariable UUID id) {
         MeseroResponseDto mesero = meseroService.findById(id);
         return ResponseEntity.ok(mesero);
    }

    @PostMapping("")
    @Operation(
        summary = "Crear nuevo mesero",
        description = "Registra un nuevo mesero en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Mesero creado exitosamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos para crear el mesero",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado para acceder a este recurso",
            content = @Content
        )
    })
    public ResponseEntity<MeseroResponseDto> save(@RequestBody MeseroRequestDto meseroRequestDto) {
        MeseroResponseDto saved = meseroService.save(meseroRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(
        summary = "Buscar mesero por nombre",
        description = "Obtiene la información de un mesero específico según su nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mesero encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MeseroResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Mesero no encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado para acceder a este recurso",
            content = @Content
        )
    })
    public ResponseEntity<MeseroResponseDto> findByNombre(@PathVariable String nombre) {
        MeseroResponseDto mesero = meseroService.findByNombre(nombre);
        return ResponseEntity.ok(mesero);
    }
}
