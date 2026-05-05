// repository/TiendaRepository.java
package com.takdelivery.repository;

import com.takdelivery.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    List<Tienda> findByActivaTrue();
    List<Tienda> findByCategoriasId(Long categoriaId);

    // ✅ Valida nombre duplicado
    boolean existsByNombre(String nombre);
}