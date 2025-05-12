package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.repository.IMeseroRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MeseroService implements IMeseroService {

    @Autowired
    private  IMeseroRepository meseroRepository;

    @Override
    public MeseroResponseDto save( MeseroRequestDto mesero) {
        try {
            Mesero user = Mesero.builder()
                    .nombre(mesero.getNombre())
                    .mail(mesero.getMail())
                    .build();

            Mesero saved = meseroRepository.save(user);
            MeseroResponseDto meseroResponseDto = MeseroResponseDto.builder()
                    .id(saved.getId())
                    .nombre(saved.getNombre())
                    .mail(saved.getMail())
                    .build();
            return meseroResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MeseroResponseDto findById(UUID id) {
        try
        {
            Mesero mesero = meseroRepository.findById(id).get();
            MeseroResponseDto meseroResponseDto = MeseroResponseDto.builder()
                    .id(mesero.getId())
                    .nombre(mesero.getNombre())
                    .mail(mesero.getMail())
                    .build();
            return meseroResponseDto;
        } catch (Exception e) {
                throw new RuntimeException(e);
        }

    }

    @Override
    public MeseroResponseDto findByNombre(String nombre) {
        try {
            Mesero mesero = meseroRepository.findByNombre(nombre);
            MeseroResponseDto meseroResponseDto = MeseroResponseDto.builder()
                    .id(mesero.getId())
                    .nombre(mesero.getNombre())
                    .mail(mesero.getMail())
                    .build();
            return meseroResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mesero findByEmail(String mail) {
       try {
            Mesero mesero = meseroRepository.findByMail(mail);
            return mesero;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
