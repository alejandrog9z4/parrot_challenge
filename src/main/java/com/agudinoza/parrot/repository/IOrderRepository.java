package com.agudinoza.parrot.repository;

import com.agudinoza.parrot.model.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<Orden, UUID> {
}
