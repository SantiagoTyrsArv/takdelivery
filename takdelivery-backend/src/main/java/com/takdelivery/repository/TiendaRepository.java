package com.takdelivery.repository;

import com.takdelivery.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    List<Tienda> findByCategoriasId(Long categoriaId);
}
