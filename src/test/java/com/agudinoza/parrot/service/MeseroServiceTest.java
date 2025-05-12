package com.agudinoza.parrot.service;

import com.agudinoza.parrot.model.dto.MeseroRequestDto;
import com.agudinoza.parrot.model.dto.MeseroResponseDto;
import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.repository.IMeseroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeseroServiceTest {

    @Mock
    private IMeseroRepository meseroRepository;

    @InjectMocks
    private MeseroService meseroService;

    private UUID meseroId;
    private Mesero mesero;
    private MeseroRequestDto meseroRequestDto;
    private MeseroResponseDto expectedResponseDto;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        meseroId = UUID.randomUUID();

        mesero = Mesero.builder()
                .id(meseroId)
                .nombre("Test Mesero")
                .mail("test@example.com")
                .build();

        meseroRequestDto = MeseroRequestDto.builder()
                .nombre("Test Mesero")
                .mail("test@example.com")
                .build();

        expectedResponseDto = MeseroResponseDto.builder()
                .id(meseroId)
                .nombre("Test Mesero")
                .mail("test@example.com")
                .build();
    }

    @Test
    void save_ValidMeseroRequest_ReturnsMeseroResponse() {
        // Arrange
        when(meseroRepository.save(any(Mesero.class))).thenReturn(mesero);

        // Act
        MeseroResponseDto result = meseroService.save(meseroRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(mesero.getId(), result.getId());
        assertEquals(mesero.getNombre(), result.getNombre());
        assertEquals(mesero.getMail(), result.getMail());

        // Verify
        verify(meseroRepository).save(any(Mesero.class));
    }

    @Test
    void save_ExceptionThrown_PropagatesRuntimeException() {
        // Arrange
        when(meseroRepository.save(any(Mesero.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            meseroService.save(meseroRequestDto);
        });

        assertTrue(exception.getMessage().contains("Database error"));

        // Verify
        verify(meseroRepository).save(any(Mesero.class));
    }

    @Test
    void findById_ExistingMesero_ReturnsMeseroResponse() {
        // Arrange
        when(meseroRepository.findById(meseroId)).thenReturn(Optional.of(mesero));

        // Act
        MeseroResponseDto result = meseroService.findById(meseroId);

        // Assert
        assertNotNull(result);
        assertEquals(mesero.getId(), result.getId());
        assertEquals(mesero.getNombre(), result.getNombre());
        assertEquals(mesero.getMail(), result.getMail());

        // Verify
        verify(meseroRepository).findById(meseroId);
    }

    @Test
    void findById_ExceptionThrown_PropagatesRuntimeException() {
        // Arrange
        when(meseroRepository.findById(meseroId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            meseroService.findById(meseroId);
        });

        assertTrue(exception.getMessage().contains("Database error"));

        // Verify
        verify(meseroRepository).findById(meseroId);
    }

    @Test
    void findByNombre_ExistingMesero_ReturnsMeseroResponse() {
        // Arrange
        String nombre = "Test Mesero";
        when(meseroRepository.findByNombre(nombre)).thenReturn(mesero);

        // Act
        MeseroResponseDto result = meseroService.findByNombre(nombre);

        // Assert
        assertNotNull(result);
        assertEquals(mesero.getId(), result.getId());
        assertEquals(mesero.getNombre(), result.getNombre());
        assertEquals(mesero.getMail(), result.getMail());

        // Verify
        verify(meseroRepository).findByNombre(nombre);
    }

    @Test
    void findByNombre_ExceptionThrown_PropagatesRuntimeException() {
        // Arrange
        String nombre = "Test Mesero";
        when(meseroRepository.findByNombre(nombre)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            meseroService.findByNombre(nombre);
        });

        assertTrue(exception.getMessage().contains("Database error"));

        // Verify
        verify(meseroRepository).findByNombre(nombre);
    }

    @Test
    void findByEmail_ExistingMesero_ReturnsMesero() {
        // Arrange
        String email = "test@example.com";
        when(meseroRepository.findByMail(email)).thenReturn(mesero);

        // Act
        Mesero result = meseroService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(mesero.getId(), result.getId());
        assertEquals(mesero.getNombre(), result.getNombre());
        assertEquals(mesero.getMail(), result.getMail());

        // Verify
        verify(meseroRepository).findByMail(email);
    }

    @Test
    void findByEmail_ExceptionThrown_PropagatesRuntimeException() {
        // Arrange
        String email = "test@example.com";
        when(meseroRepository.findByMail(email)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            meseroService.findByEmail(email);
        });

        assertTrue(exception.getMessage().contains("Database error"));

        // Verify
        verify(meseroRepository).findByMail(email);
    }
}