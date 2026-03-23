package com.takdelivery.repository;

import com.takdelivery.model.CarritoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoCompra, Long> {
    Optional<CarritoCompra> findByClienteId(Long clienteId);
}
