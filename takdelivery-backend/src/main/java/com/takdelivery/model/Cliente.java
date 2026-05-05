// model/Cliente.java
package com.takdelivery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    private static final int MAX_DIRECCIONES = 5;

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<DireccionEntrega> direcciones = new ArrayList<>();

    public Cliente() {}

    public Cliente(String nombre, String email, String contrasena, String telefono) {
        this.setNombre(nombre);
        this.setEmail(email);
        this.setContrasena(contrasena);
        this.setTelefono(telefono);
    }

    // ✅ Encapsulamiento — el Cliente sabe si puede agregar más direcciones
    public void validarPuedeAgregarDireccion() {
        if (this.direcciones.size() >= MAX_DIRECCIONES) {
            throw new IllegalArgumentException(
                    "No puedes tener más de " + MAX_DIRECCIONES + " direcciones registradas");
        }
    }

    public void agregarDireccion(DireccionEntrega d) {
        d.setCliente(this);
        this.direcciones.add(d);
    }

    public void eliminarDireccion(DireccionEntrega d) {
        this.direcciones.remove(d);
    }

    public List<DireccionEntrega> getDirecciones() { return direcciones; }
}