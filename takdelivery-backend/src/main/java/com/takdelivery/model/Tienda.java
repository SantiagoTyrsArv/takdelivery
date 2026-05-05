// model/Tienda.java
package com.takdelivery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tiendas")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private double calificacion;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean activa;

    @ManyToMany
    @JoinTable(
            name = "tienda_categoria",
            joinColumns = @JoinColumn(name = "tienda_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private final List<Categoria> categorias = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Producto> productos = new ArrayList<>();

    public Tienda() {}

    public Tienda(String nombre, String descripcion,
                  LocalTime horarioApertura, LocalTime horarioCierre) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.activa = true;
        this.calificacion = 0.0;
    }

    public boolean estaAbierta() {
        LocalTime ahora = LocalTime.now();
        return activa && ahora.isAfter(horarioApertura) && ahora.isBefore(horarioCierre);
    }

    // ✅ Encapsulamiento — la Tienda valida disponibilidad completa (activa + horario)
    public void validarActiva() {
        if (!this.activa) {
            throw new IllegalArgumentException("La tienda no está disponible");
        }
        if (!estaAbierta()) {
            throw new IllegalArgumentException(
                    "La tienda está cerrada. Horario: " + horarioApertura + " - " + horarioCierre);
        }
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
    public LocalTime getHorarioApertura() { return horarioApertura; }
    public void setHorarioApertura(LocalTime h) { this.horarioApertura = h; }
    public LocalTime getHorarioCierre() { return horarioCierre; }
    public void setHorarioCierre(LocalTime h) { this.horarioCierre = h; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public List<Categoria> getCategorias() { return categorias; }
    public List<Producto> getProductos() { return productos; }
}