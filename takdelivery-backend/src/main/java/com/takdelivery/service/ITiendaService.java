package com.takdelivery.service;

import com.takdelivery.model.Tienda;
import com.takdelivery.repository.TiendaRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TiendaService {
    private final TiendaRepository tiendaRepository;

    public List<Tienda> obtenerTodas() {
        return tiendaRepository.findAll();
    }

    public Tienda obtenerPorId(Long id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
    }

    public List<Tienda> obtenerPorCategoria(Long categoriaId) {
        return tiendaRepository.findByCategoriasId(categoriaId);
    }

    public Tienda crear(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }
}
