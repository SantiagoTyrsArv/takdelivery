// dto/TiendaResponseDTO.java
package com.takdelivery.dto;

import java.time.LocalTime;

public class TiendaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private double calificacion;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean activa;
    private boolean abiertaAhora;

    public TiendaResponseDTO(Long id, String nombre, String descripcion,
                             double calificacion, LocalTime horarioApertura,
                             LocalTime horarioCierre, boolean activa, boolean abiertaAhora) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.activa = activa;
        this.abiertaAhora = abiertaAhora;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getCalificacion() { return calificacion; }
    public LocalTime getHorarioApertura() { return horarioApertura; }
    public LocalTime getHorarioCierre() { return horarioCierre; }
    public boolean isActiva() { return activa; }
    public boolean isAbiertaAhora() { return abiertaAhora; }
}