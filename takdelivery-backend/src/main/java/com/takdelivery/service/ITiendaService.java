// service/ITiendaService.java
package com.takdelivery.service;

import com.takdelivery.dto.TiendaRequestDTO;
import com.takdelivery.dto.TiendaResponseDTO;
import com.takdelivery.dto.TiendaUpdateDTO;
import java.util.List;

public interface ITiendaService {
    List<TiendaResponseDTO> listarActivas();
    TiendaResponseDTO obtenerPorId(Long id);
    List<TiendaResponseDTO> listarPorCategoria(Long categoriaId);
    TiendaResponseDTO crear(TiendaRequestDTO dto);
    TiendaResponseDTO actualizar(Long id, TiendaRequestDTO dto);
    TiendaResponseDTO actualizarParcial(Long id, TiendaUpdateDTO dto);
}