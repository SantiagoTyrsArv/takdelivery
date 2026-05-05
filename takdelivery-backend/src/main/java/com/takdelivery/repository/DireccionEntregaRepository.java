package com.takdelivery.repository;

import com.takdelivery.model.DireccionEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionEntregaRepository extends JpaRepository<DireccionEntrega, Long> {
    List<DireccionEntrega> findByClienteId(Long clienteId);
}
