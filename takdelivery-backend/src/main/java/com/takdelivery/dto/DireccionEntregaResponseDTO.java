// dto/DireccionEntregaResponseDTO.java
package com.takdelivery.dto;

public class DireccionEntregaResponseDTO {

    private Long id;
    private String alias;
    private String direccion;
    private String ciudad;
    private String barrio;
    private double latitud;
    private double longitud;

    public DireccionEntregaResponseDTO(Long id, String alias, String direccion,
                                       String ciudad, String barrio,
                                       double latitud, double longitud) {
        this.id = id;
        this.alias = alias;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() { return id; }
    public String getAlias() { return alias; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public String getBarrio() { return barrio; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
}