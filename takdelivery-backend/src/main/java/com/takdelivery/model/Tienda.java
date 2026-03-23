package com.takdelivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tiendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    private double calificacion;
    
    @Column(name = "horario_apertura")
    private LocalTime horarioApertura;
    
    @Column(name = "horario_cierre")
    private LocalTime horarioCierre;
    
    private boolean activa;
    
    @ManyToMany
    @JoinTable(
        name = "tienda_categoria",
        joinColumns = @JoinColumn(name = "tienda_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();
}
