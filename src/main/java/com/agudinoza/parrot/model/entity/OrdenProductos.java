package com.agudinoza.parrot.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orden_productos")
public class OrdenProductos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio_unitario;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;
}
