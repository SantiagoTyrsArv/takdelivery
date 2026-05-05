package com.takdelivery.dto;

public class ClienteUpdateDTO {

    private String nombre;
    private String telefono;

    public ClienteUpdateDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
