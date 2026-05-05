// controller/CarritoController.java
package com.takdelivery.controller;

import com.takdelivery.dto.ActualizarCantidadDTO;
import com.takdelivery.dto.CarritoResponseDTO;
import com.takdelivery.dto.ItemCarritoDTO;
import com.takdelivery.service.ICarritoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final ICarritoService carritoService;

    public CarritoController(ICarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<CarritoResponseDTO> obtenerCarrito(@PathVariable Long clienteId) {
        return ResponseEntity.ok(carritoService.obtenerCarrito(clienteId));
    }

    @PostMapping("/{clienteId}/items")
    public ResponseEntity<CarritoResponseDTO> agregarItem(
            @PathVariable Long clienteId,
            @Valid @RequestBody ItemCarritoDTO dto) {
        return ResponseEntity.ok(carritoService.agregarItem(clienteId, dto));
    }

    @PutMapping("/{clienteId}/items/{productoId}")
    public ResponseEntity<CarritoResponseDTO> actualizarCantidad(
            @PathVariable Long clienteId,
            @PathVariable Long productoId,
            @Valid @RequestBody ActualizarCantidadDTO dto) {
        return ResponseEntity.ok(carritoService.actualizarCantidad(clienteId, productoId, dto.getCantidad()));
    }

    @DeleteMapping("/{clienteId}/items/{productoId}")
    public ResponseEntity<CarritoResponseDTO> eliminarItem(
            @PathVariable Long clienteId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(carritoService.eliminarItem(clienteId, productoId));
    }

    @DeleteMapping("/{clienteId}/vaciar")
    public ResponseEntity<Void> vaciar(@PathVariable Long clienteId) {
        carritoService.vaciar(clienteId);
        return ResponseEntity.noContent().build();
    }
}