package com.takdelivery.dto;

import jakarta.validation.constraints.Positive;

public class ActualizarCantidadDTO {

    @Positive(message = "La cantidad debe ser mayor a cero")
    private int cantidad;

    public ActualizarCantidadDTO() {}

    public ActualizarCantidadDTO(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
