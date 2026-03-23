package com.takdelivery.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
