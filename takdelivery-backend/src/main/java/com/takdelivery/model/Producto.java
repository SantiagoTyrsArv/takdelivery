package com.takdelivery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private double peso;

    @Column(nullable = false)
    private double precio;

    @Column(name = "precio_promocion")
    private double precioPromocion;

    private String imagen;
    private boolean activo;

    @Column(name = "stock_disponible")
    private int stockDisponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    public Producto() {}

    public Producto(Long id, String nombre, String descripcion, double peso, double precio,
                    double precioPromocion, String imagen, boolean activo, int stockDisponible, Tienda tienda) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peso = peso;
        this.precio = precio;
        this.precioPromocion = precioPromocion;
        this.imagen = imagen;
        this.activo = activo;
        this.stockDisponible = stockDisponible;
        this.tienda = tienda;
    }

    // ✅ Encapsulamiento — el Producto valida su propia disponibilidad
    public void validarDisponible(int cantidadSolicitada) {
        if (!activo) {
            throw new IllegalArgumentException("El producto '" + nombre + "' no está disponible");
        }
        if (stockDisponible < cantidadSolicitada) {
            throw new IllegalArgumentException("Stock insuficiente para '" + nombre +
                    "'. Disponible: " + stockDisponible + ", solicitado: " + cantidadSolicitada);
        }
    }

    /** Retorna el precio de promoción si existe, de lo contrario el precio normal */
    public double getPrecioActual() {
        return precioPromocion > 0 ? precioPromocion : precio;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public double getPrecioPromocion() { return precioPromocion; }
    public void setPrecioPromocion(double precioPromocion) { this.precioPromocion = precioPromocion; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public int getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
}
