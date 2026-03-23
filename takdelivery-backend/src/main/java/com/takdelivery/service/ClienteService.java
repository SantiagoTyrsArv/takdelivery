package com.takdelivery.service;

import com.takdelivery.dto.LoginRequestDTO;
import com.takdelivery.dto.RegistroRequestDTO;
import com.takdelivery.model.Cliente;
import com.takdelivery.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Cliente registrarCliente(RegistroRequestDTO dto) {
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setContrasena(dto.getContrasena()); // Debería bcrypt en prod
        cliente.setTelefono(dto.getTelefono());
        return clienteRepository.save(cliente);
    }
    
    public Cliente login(LoginRequestDTO dto) {
        return clienteRepository.findByEmailAndContrasena(dto.getEmail(), dto.getContrasena())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
    
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
}
