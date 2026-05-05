package com.takdelivery.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "carritos")
public class CarritoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    public CarritoCompra() {}

    public CarritoCompra(Cliente cliente) {
        this.cliente = cliente;
    }

    // ✅ Encapsulamiento — el Carrito sabe si está vacío
    public void validarNoVacio() {
        if (this.items.isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }
    }

    // ✅ Encapsulamiento — busca un item por productoId
    public Optional<ItemCarrito> buscarItem(Long productoId) {
        return this.items.stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst();
    }

    public void agregarOActualizar(ItemCarrito nuevoItem) {
        nuevoItem.setCarrito(this); // ← agrega esta línea
        buscarItem(nuevoItem.getProducto().getId())
                .ifPresentOrElse(
                        i -> i.setCantidad(i.getCantidad() + nuevoItem.getCantidad()),
                        () -> this.items.add(nuevoItem)
                );
    }

    // ✅ Encapsulamiento — actualiza cantidad de un item existente
    public ItemCarrito actualizarCantidadItem(Long productoId, int cantidad) {
        ItemCarrito item = buscarItem(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Item con productoId " + productoId + " no encontrado en el carrito"));
        item.setCantidad(cantidad);
        return item;
    }

    // ✅ Encapsulamiento — el Carrito calcula su propio total
    public double calcularTotal() {
        return this.items.stream()
                .mapToDouble(i -> i.getProducto().getPrecioActual() * i.getCantidad())
                .sum();
    }

    public void removerItem(ItemCarrito item) {
        this.items.remove(item);
    }

    public void vaciar() {
        this.items.clear();
    }

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemCarrito> getItems() { return items; }
}