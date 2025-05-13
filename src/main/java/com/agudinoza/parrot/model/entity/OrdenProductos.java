package com.agudinoza.parrot.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orden_productos", indexes = {
        @Index(name = "fecha_creacion_idx", columnList = "fecha_creacion"),
        @Index(name = "nombre_idx", columnList = "nombre")
})
public class OrdenProductos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio_unitario;

    private Integer cantidad;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;
}
