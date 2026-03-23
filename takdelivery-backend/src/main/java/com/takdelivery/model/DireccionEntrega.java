package com.takdelivery.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direcciones_entrega")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEntrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String alias;
    
    @Column(nullable = false)
    private String direccion;
    
    private String ciudad;
    private String barrio;
    private Double latitud;
    private Double longitud;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
