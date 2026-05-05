// service/impl/ClienteServiceImpl.java
package com.takdelivery.service.impl;

import com.takdelivery.dto.ClienteRequestDTO;
import com.takdelivery.dto.ClienteResponseDTO;
import com.takdelivery.dto.ClienteUpdateDTO;
import com.takdelivery.dto.DireccionEntregaRequestDTO;
import com.takdelivery.dto.DireccionEntregaResponseDTO;
import com.takdelivery.exception.ResourceNotFoundException;
import com.takdelivery.model.Cliente;
import com.takdelivery.model.DireccionEntrega;
import com.takdelivery.repository.ClienteRepository;
import com.takdelivery.repository.DireccionEntregaRepository;
import com.takdelivery.service.IClienteService;
import com.takdelivery.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    private static final String CLIENTE = "Cliente";

    private final ClienteRepository clienteRepository;
    private final DireccionEntregaRepository direccionRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              DireccionEntregaRepository direccionRepository,
                              PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClienteResponseDTO registrar(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        String contrasenaHasheada = passwordEncoder.encode(dto.getContrasena());
        Cliente cliente = new Cliente(dto.getNombre(), dto.getEmail(),
                contrasenaHasheada, dto.getTelefono());
        clienteRepository.save(cliente);
        return toDTO(cliente);
    }

    @Override
    public ClienteResponseDTO obtenerPerfil(Long id) {
        return toDTO(buscarCliente(id));
    }

    @Override
    public ClienteResponseDTO actualizarPerfil(Long id, ClienteUpdateDTO dto) {
        Cliente cliente = buscarCliente(id);

        // ✅ Valida que no sea vacío ni solo espacios
        if (dto.getNombre() != null && !dto.getNombre().isBlank())
            cliente.setNombre(dto.getNombre());
        if (dto.getTelefono() != null)
            cliente.setTelefono(dto.getTelefono());

        clienteRepository.save(cliente);
        return toDTO(cliente);
    }

    @Override
    public void agregarDireccion(Long clienteId, DireccionEntregaRequestDTO dto) {
        Cliente cliente = buscarCliente(clienteId);

        // ✅ Delega validación al modelo — Encapsulamiento
        cliente.validarPuedeAgregarDireccion();

        DireccionEntrega direccion = new DireccionEntrega(
                dto.getAlias(), dto.getDireccion(), dto.getCiudad(),
                dto.getBarrio(), dto.getLatitud(), dto.getLongitud()
        );
        cliente.agregarDireccion(direccion);
        clienteRepository.save(cliente);
    }

    @Override
    public void eliminarDireccion(Long clienteId, Long direccionId) {
        Cliente cliente = buscarCliente(clienteId);
        DireccionEntrega direccion = direccionRepository.findById(direccionId)
                .orElseThrow(() -> new ResourceNotFoundException("Direccion", direccionId));

        // ✅ Delega validación al modelo — Encapsulamiento
        direccion.validarPerteneceA(clienteId);

        cliente.eliminarDireccion(direccion);
        clienteRepository.save(cliente);
    }

    @Override
    public List<DireccionEntregaResponseDTO> listarDirecciones(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException(CLIENTE, clienteId);
        }
        // ✅ Retorna DTO en lugar de entidad
        return direccionRepository.findByClienteId(clienteId)
                .stream().map(this::toDireccionDTO).toList();
    }

    // ── Métodos privados ──────────────────────────

    private Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE, id));
    }

    private ClienteResponseDTO toDTO(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(), cliente.getNombre(),
                cliente.getEmail(), cliente.getTelefono());
    }

    private DireccionEntregaResponseDTO toDireccionDTO(DireccionEntrega d) {
        return new DireccionEntregaResponseDTO(
                d.getId(), d.getAlias(), d.getDireccion(),
                d.getCiudad(), d.getBarrio(), d.getLatitud(), d.getLongitud()
        );
    }
}