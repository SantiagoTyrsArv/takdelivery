package com.takdelivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemCarritoDTO {

    @NotNull(message = "El productoId es obligatorio")
    private Long productoId;

    @Positive(message = "La cantidad debe ser mayor a cero")
    private int cantidad;

    public ItemCarritoDTO() {}

    public ItemCarritoDTO(Long productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
