package com.takdelivery.controller;

import com.takdelivery.dto.TiendaRequestDTO;
import com.takdelivery.dto.TiendaResponseDTO;
import com.takdelivery.dto.TiendaUpdateDTO;
import com.takdelivery.service.ITiendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tiendas")
public class TiendaController {

    private final ITiendaService tiendaService;

    public TiendaController(ITiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @GetMapping
    public ResponseEntity<List<TiendaResponseDTO>> listar() {
        return ResponseEntity.ok(tiendaService.listarActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiendaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(tiendaService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<TiendaResponseDTO>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(tiendaService.listarPorCategoria(categoriaId));
    }

    @PostMapping
    public ResponseEntity<TiendaResponseDTO> crear(@Valid @RequestBody TiendaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tiendaService.crear(dto));
    }

    // ✅ NUEVO: PUT — actualización completa
    @PutMapping("/{id}")
    public ResponseEntity<TiendaResponseDTO> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody TiendaRequestDTO dto) {
        return ResponseEntity.ok(tiendaService.actualizar(id, dto));
    }

    // ✅ NUEVO: PATCH — actualización parcial
    @PatchMapping("/{id}")
    public ResponseEntity<TiendaResponseDTO> actualizarParcial(@PathVariable Long id,
                                                    @RequestBody TiendaUpdateDTO dto) {
        return ResponseEntity.ok(tiendaService.actualizarParcial(id, dto));
    }
}