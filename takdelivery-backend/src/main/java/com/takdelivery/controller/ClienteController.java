// controller/ClienteController.java
package com.takdelivery.controller;

import com.takdelivery.dto.ClienteRequestDTO;
import com.takdelivery.dto.ClienteResponseDTO;
import com.takdelivery.dto.ClienteUpdateDTO;
import com.takdelivery.dto.DireccionEntregaRequestDTO;
import com.takdelivery.dto.DireccionEntregaResponseDTO;
import com.takdelivery.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registro")
    public ResponseEntity<ClienteResponseDTO> registro(
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPerfil(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody ClienteUpdateDTO dto) {
        return ResponseEntity.ok(clienteService.actualizarPerfil(id, dto));
    }

    @GetMapping("/{id}/direcciones")
    public ResponseEntity<List<DireccionEntregaResponseDTO>> listarDirecciones(
            @PathVariable Long id) {
        // ✅ Retorna DTO en lugar de entidad
        return ResponseEntity.ok(clienteService.listarDirecciones(id));
    }

    @PostMapping("/{id}/direcciones")
    public ResponseEntity<Void> agregarDireccion(
            @PathVariable Long id,
            @Valid @RequestBody DireccionEntregaRequestDTO dto) {
        clienteService.agregarDireccion(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/direcciones/{direccionId}")
    public ResponseEntity<Void> eliminarDireccion(
            @PathVariable Long id,
            @PathVariable Long direccionId) {
        clienteService.eliminarDireccion(id, direccionId);
        return ResponseEntity.noContent().build();
    }
}