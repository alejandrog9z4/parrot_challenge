package com.agudinoza.parrot.repository;

import com.agudinoza.parrot.model.entity.Mesero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMeseroRepository extends JpaRepository<Mesero, UUID> {
    Mesero findByNombre(String nombre);
    Mesero findByMail(String mail);
}
