package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.OrdenRequestDto;
import com.agudinoza.parrot.model.dto.OrdenResponseDto;

import java.util.List;
import java.util.UUID;

public interface IOrdenService {
    public OrdenResponseDto findById(UUID id);
    public UUID createOrden(OrdenRequestDto ordenRequestDto);
    public List<OrdenRequestDto> findByMeseroId(UUID meseroId);

}
