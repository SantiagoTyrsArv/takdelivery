package com.takdelivery.service;

import com.takdelivery.model.*;
import com.takdelivery.model.enums.*;
import com.takdelivery.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagoService pagoService;

    public CarritoCompra obtenerCarrito(Long clienteId) {
        return carritoRepository.findByClienteId(clienteId)
                .orElseGet(() -> {
                    Cliente cliente = clienteRepository.findById(clienteId)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                    CarritoCompra nuevoCarrito = new CarritoCompra();
                    nuevoCarrito.setCliente(cliente);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    @Transactional
    public CarritoCompra agregarItem(Long clienteId, Long productoId, int cantidad) {
        CarritoCompra carrito = obtenerCarrito(clienteId);
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                
        // Verificar si el producto ya está en el carrito
        for (ItemCarrito item : carrito.getItems()) {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + cantidad);
                return carritoRepository.save(carrito);
            }
        }
        
        ItemCarrito nuevoItem = new ItemCarrito();
        nuevoItem.setCarrito(carrito);
        nuevoItem.setProducto(producto);
        nuevoItem.setCantidad(cantidad);
        carrito.getItems().add(nuevoItem);
        
        return carritoRepository.save(carrito);
    }

    @Transactional
    public CarritoCompra eliminarItem(Long clienteId, Long productoId) {
        CarritoCompra carrito = obtenerCarrito(clienteId);
        carrito.getItems().removeIf(item -> item.getProducto().getId().equals(productoId));
        return carritoRepository.save(carrito);
    }

    @Transactional
    public Pedido confirmarCarrito(Long clienteId, TipoPago tipoPago, TipoEnvio tipoEnvio) {
        CarritoCompra carrito = obtenerCarrito(clienteId);
        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Asumimos que todos los items son de la misma tienda (Tomamos la del primer item)
        Tienda tienda = carrito.getItems().get(0).getProducto().getTienda();
        
        Pedido pedido = new Pedido();
        pedido.setCliente(carrito.getCliente());
        pedido.setTienda(tienda);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setTipoPago(tipoPago);
        pedido.setTipoEnvio(tipoEnvio);
        pedido.setNotas("Pedido generado desde carrito web");

        double subtotalProductos = 0.0;
        double pesoTotal = 0.0;
        
        List<DetallePedido> detalles = new ArrayList<>();
        for (ItemCarrito item : carrito.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            
            double precioCobrar = item.getProducto().getPrecioPromocion() > 0 
                ? item.getProducto().getPrecioPromocion() 
                : item.getProducto().getPrecio();
                
            detalle.setPrecioUnitario(precioCobrar);
            double subtotalItem = precioCobrar * item.getCantidad();
            detalle.setSubtotal(subtotalItem);
            
            subtotalProductos += subtotalItem;
            pesoTotal += item.getProducto().getPeso() * item.getCantidad();
            
            // Regla: Reducir stock (opcional en requerimiento, pero útil)
            Producto p = item.getProducto();
            if (p.getStockDisponible() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + p.getNombre());
            }
            p.setStockDisponible(p.getStockDisponible() - item.getCantidad());
            productoRepository.save(p);
            
            detalles.add(detalle);
        }
        pedido.setDetalles(detalles);

        // Regla 6: Calculo de costo según Peso
        double tarifaEnvio = 0.0;
        if (tipoEnvio == TipoEnvio.ESTANDAR) tarifaEnvio = 5000.0 * pesoTotal;
        else if (tipoEnvio == TipoEnvio.EXPRESS) tarifaEnvio = 8000.0 * pesoTotal;
        else if (tipoEnvio == TipoEnvio.INTERNACIONAL) tarifaEnvio = 15000.0 * pesoTotal;
        
        pedido.setCostoEnvio(tarifaEnvio);
        pedido.setTotal(subtotalProductos + tarifaEnvio);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Vaciar carrito
        carrito.getItems().clear();
        carritoRepository.save(carrito);

        // Procesar pago (Regla 5 y 6)
        pagoService.procesarPago(pedidoGuardado);

        return pedidoGuardado;
    }
}
