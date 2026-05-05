// service/IClienteService.java
package com.takdelivery.service;

import com.takdelivery.dto.ClienteRequestDTO;
import com.takdelivery.dto.ClienteResponseDTO;
import com.takdelivery.dto.ClienteUpdateDTO;
import com.takdelivery.dto.DireccionEntregaRequestDTO;
import com.takdelivery.dto.DireccionEntregaResponseDTO;
import java.util.List;

public interface IClienteService {
    ClienteResponseDTO registrar(ClienteRequestDTO dto);
    ClienteResponseDTO obtenerPerfil(Long id);
    ClienteResponseDTO actualizarPerfil(Long id, ClienteUpdateDTO dto);
    void agregarDireccion(Long clienteId, DireccionEntregaRequestDTO dto);
    void eliminarDireccion(Long clienteId, Long direccionId);
    // ✅ Retorna DTO en lugar de entidad
    List<DireccionEntregaResponseDTO> listarDirecciones(Long clienteId);
}