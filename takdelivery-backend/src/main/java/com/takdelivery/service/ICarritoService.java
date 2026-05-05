// service/ICarritoService.java
package com.takdelivery.service;

import com.takdelivery.dto.CarritoResponseDTO;
import com.takdelivery.dto.ItemCarritoDTO;

public interface ICarritoService {
    CarritoResponseDTO obtenerCarrito(Long clienteId);
    CarritoResponseDTO agregarItem(Long clienteId, ItemCarritoDTO dto);
    CarritoResponseDTO actualizarCantidad(Long clienteId, Long productoId, int cantidad);
    CarritoResponseDTO eliminarItem(Long clienteId, Long productoId);
    void vaciar(Long clienteId);
}