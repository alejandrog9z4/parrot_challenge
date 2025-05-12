package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;

import java.util.UUID;

public interface IMeseroService {
    public MeseroResponseDto save(MeseroRequestDto mesero);
    public MeseroResponseDto findById(UUID id);
    public MeseroResponseDto findByNombre(String nombre);
    public Mesero findByEmail(String email);
}
