package com.agudinoza.parrot.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeseroResponseDto {
    private UUID id;
    private String nombre;
    private String mail;
}
