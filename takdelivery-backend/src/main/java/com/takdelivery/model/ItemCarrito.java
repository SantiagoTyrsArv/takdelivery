package com.takdelivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "items_carrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private CarritoCompra carrito;



    public ItemCarrito() {}

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecioActual() * cantidad;
    }

    public Long getId() { return id; }
    public Producto getProducto() { return producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public CarritoCompra getCarrito() { return carrito; }
    public void setCarrito(CarritoCompra carrito) { this.carrito = carrito; }
}