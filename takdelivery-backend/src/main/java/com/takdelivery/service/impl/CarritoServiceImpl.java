// service/impl/CarritoServiceImpl.java
package com.takdelivery.service.impl;

import com.takdelivery.dto.CarritoResponseDTO;
import com.takdelivery.dto.ItemCarritoDTO;
import com.takdelivery.dto.ItemCarritoResponseDTO;
import com.takdelivery.exception.ResourceNotFoundException;
import com.takdelivery.model.CarritoCompra;
import com.takdelivery.model.ItemCarrito;
import com.takdelivery.model.Producto;
import com.takdelivery.model.Cliente;
import com.takdelivery.repository.CarritoRepository;
import com.takdelivery.repository.ClienteRepository;
import com.takdelivery.repository.ProductoRepository;
import com.takdelivery.service.ICarritoService;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements ICarritoService {

    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public CarritoServiceImpl(CarritoRepository carritoRepository,
                              ClienteRepository clienteRepository,
                              ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public CarritoResponseDTO obtenerCarrito(Long clienteId) {
        CarritoCompra carrito = carritoRepository.findByClienteId(clienteId)
                .orElseGet(() -> crearCarrito(clienteId));
        return toDTO(carrito);
    }

    @Override
    public CarritoResponseDTO agregarItem(Long clienteId, ItemCarritoDTO dto) {
        CarritoCompra carrito = carritoRepository.findByClienteId(clienteId)
                .orElseGet(() -> crearCarrito(clienteId));
        Producto producto = buscarProducto(dto.getProductoId());

        // ✅ Delega validación al modelo — Encapsulamiento
        producto.validarDisponible(dto.getCantidad());

        // ✅ Delega lógica de agregado al carrito — Encapsulamiento
        carrito.agregarOActualizar(new ItemCarrito(producto, dto.getCantidad()));

        return toDTO(carritoRepository.save(carrito));
    }

    @Override
    public CarritoResponseDTO actualizarCantidad(Long clienteId, Long productoId, int cantidad) {
        CarritoCompra carrito = carritoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no tiene carrito activo"));
        Producto producto = buscarProducto(productoId);

        // ✅ Delega validación al modelo — Encapsulamiento
        producto.validarDisponible(cantidad);

        // ✅ Delega actualización al carrito — Encapsulamiento
        carrito.actualizarCantidadItem(productoId, cantidad);

        return toDTO(carritoRepository.save(carrito));
    }

    @Override
    public CarritoResponseDTO eliminarItem(Long clienteId, Long productoId) {
        CarritoCompra carrito = carritoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no tiene carrito activo"));

        // ✅ Delega búsqueda al carrito — Encapsulamiento
        ItemCarrito item = carrito.buscarItem(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Item en carrito", productoId));

        carrito.removerItem(item);
        return toDTO(carritoRepository.save(carrito));
    }

    @Override
    public void vaciar(Long clienteId) {
        CarritoCompra carrito = carritoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no tiene carrito activo"));
        carrito.vaciar();
        carritoRepository.save(carrito);
    }

    // ── Métodos privados ──────────────────────────

    private Producto buscarProducto(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", productoId));
    }

    private CarritoCompra crearCarrito(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", clienteId));
        return carritoRepository.save(new CarritoCompra(cliente));
    }

    private CarritoResponseDTO toDTO(CarritoCompra carrito) {
        var items = carrito.getItems().stream()
                .map(i -> new ItemCarritoResponseDTO(
                        i.getProducto().getId(),
                        i.getProducto().getNombre(),
                        i.getCantidad(),
                        i.getProducto().getPrecioActual()
                )).toList();
        return new CarritoResponseDTO(
                carrito.getId(),
                carrito.getCliente().getId(),
                items,
                carrito.calcularTotal()
        );
    }
}