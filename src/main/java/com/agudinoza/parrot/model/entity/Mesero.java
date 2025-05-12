package com.agudinoza.parrot.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Immutable
@Table(name = "mesero", indexes = {
        @Index(name = "mesero_name_idx", columnList = "nombre")
})
public class Mesero {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String nombre;

    @Column(unique = true)
    private String mail;

    public String getMail() {
        return mail;
    }

}
