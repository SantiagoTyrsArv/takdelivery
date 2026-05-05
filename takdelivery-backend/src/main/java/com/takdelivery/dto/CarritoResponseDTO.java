package com.takdelivery.dto;

import java.util.List;

public class CarritoResponseDTO {

    private Long carritoId;
    private Long clienteId;
    private List<ItemCarritoResponseDTO> items;
    private double total;

    public CarritoResponseDTO(Long carritoId, Long clienteId, List<ItemCarritoResponseDTO> items, double total) {
        this.carritoId = carritoId;
        this.clienteId = clienteId;
        this.items = items;
        this.total = total;
    }

    public Long getCarritoId() { return carritoId; }
    public Long getClienteId() { return clienteId; }
    public List<ItemCarritoResponseDTO> getItems() { return items; }
    public double getTotal() { return total; }
}
