// service/impl/TiendaServiceImpl.java
package com.takdelivery.service.impl;

import com.takdelivery.dto.TiendaRequestDTO;
import com.takdelivery.dto.TiendaResponseDTO;
import com.takdelivery.dto.TiendaUpdateDTO;
import com.takdelivery.exception.ResourceNotFoundException;
import com.takdelivery.model.Tienda;
import com.takdelivery.repository.TiendaRepository;
import com.takdelivery.service.ITiendaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TiendaServiceImpl implements ITiendaService {

    private final TiendaRepository tiendaRepository;

    public TiendaServiceImpl(TiendaRepository tiendaRepository) {
        this.tiendaRepository = tiendaRepository;
    }

    @Override
    public List<TiendaResponseDTO> listarActivas() {
        return tiendaRepository.findByActivaTrue()
                .stream().map(this::toDTO).toList();
    }

    @Override
    public TiendaResponseDTO obtenerPorId(Long id) {
        return toDTO(buscarTienda(id));
    }

    @Override
    public List<TiendaResponseDTO> listarPorCategoria(Long categoriaId) {
        return tiendaRepository.findByCategoriasId(categoriaId)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public TiendaResponseDTO crear(TiendaRequestDTO dto) {
        // ✅ Valida nombre duplicado
        if (tiendaRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una tienda con ese nombre");
        }

        Tienda tienda = new Tienda(
                dto.getNombre(), dto.getDescripcion(),
                dto.getHorarioApertura(), dto.getHorarioCierre()
        );
        return toDTO(tiendaRepository.save(tienda));
    }

    @Override
    public TiendaResponseDTO actualizar(Long id, TiendaRequestDTO dto) {
        Tienda tienda = buscarTienda(id);
        tienda.setNombre(dto.getNombre());
        tienda.setDescripcion(dto.getDescripcion());
        tienda.setHorarioApertura(dto.getHorarioApertura());
        tienda.setHorarioCierre(dto.getHorarioCierre());
        return toDTO(tiendaRepository.save(tienda));
    }

    @Override
    public TiendaResponseDTO actualizarParcial(Long id, TiendaUpdateDTO dto) {
        Tienda tienda = buscarTienda(id);

        // ✅ Valida que no sea vacío ni solo espacios
        if (dto.getNombre() != null && !dto.getNombre().isBlank())
            tienda.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null && !dto.getDescripcion().isBlank())
            tienda.setDescripcion(dto.getDescripcion());
        if (dto.getActiva() != null)
            tienda.setActiva(dto.getActiva());
        if (dto.getHorarioApertura() != null)
            tienda.setHorarioApertura(dto.getHorarioApertura());
        if (dto.getHorarioCierre() != null)
            tienda.setHorarioCierre(dto.getHorarioCierre());

        return toDTO(tiendaRepository.save(tienda));
    }

    // ── Métodos privados ──────────────────────────

    private Tienda buscarTienda(Long id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tienda", id));
    }

    private TiendaResponseDTO toDTO(Tienda tienda) {
        return new TiendaResponseDTO(
                tienda.getId(), tienda.getNombre(), tienda.getDescripcion(),
                tienda.getCalificacion(), tienda.getHorarioApertura(),
                tienda.getHorarioCierre(), tienda.isActiva(),
                tienda.estaAbierta()
        );
    }
}