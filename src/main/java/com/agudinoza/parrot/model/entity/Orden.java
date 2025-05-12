package com.agudinoza.parrot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String nombre_cliente;

    private Double total;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "mesero_id", nullable = false)
    private Mesero mesero;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenProductos> productos;


}
