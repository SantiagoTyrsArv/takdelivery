package com.takdelivery.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "repartidores")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Repartidor extends Usuario {
    
    private boolean disponible;
    private String vehiculo;
    private String placa;
    
    @Column(name = "calificacion_promedio")
    private double calificacionPromedio;
}
