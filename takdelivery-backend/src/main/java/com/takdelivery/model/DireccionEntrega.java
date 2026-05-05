// model/DireccionEntrega.java
package com.takdelivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "direcciones_entrega")
public class DireccionEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alias;
    private String direccion;
    private String ciudad;
    private String barrio;
    private double latitud;
    private double longitud;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public DireccionEntrega() {}

    public DireccionEntrega(String alias, String direccion, String ciudad,
                            String barrio, double latitud, double longitud) {
        this.alias = alias;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // ✅ Encapsulamiento — la Dirección sabe a quién pertenece
    public void validarPerteneceA(Long clienteId) {
        if (!this.cliente.getId().equals(clienteId)) {
            throw new IllegalArgumentException("La dirección no pertenece a este cliente");
        }
    }

    public Long getId() { return id; }
    public String getAlias() { return alias; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public String getBarrio() { return barrio; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}