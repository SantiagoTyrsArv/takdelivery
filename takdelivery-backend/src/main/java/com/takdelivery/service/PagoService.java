package com.takdelivery.service;

import com.takdelivery.model.Pago;
import com.takdelivery.model.Pedido;
import com.takdelivery.model.enums.EstadoPago;
import com.takdelivery.model.enums.EstadoPedido;
import com.takdelivery.repository.PagoRepository;
import com.takdelivery.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;
    private final PedidoRepository pedidoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pago procesarPago(Pedido pedido) {
        // Regla 5: Math.random() > 0.2 aprueba
        boolean aprobado = Math.random() > 0.2;
        
        Pago pago = new Pago();
        pago.setMonto(pedido.getTotal());
        pago.setPedido(pedido);
        
        if (aprobado) {
            pago.setEstado(EstadoPago.APROBADO);
            pedido.setEstado(EstadoPedido.CONFIRMADO);
        } else {
            pago.setEstado(EstadoPago.RECHAZADO);
            pedido.setEstado(EstadoPedido.CANCELADO);
        }
        
        // Guardar pago
        Pago savedPago = pagoRepository.save(pago);
        
        // Actualizar el estado del pedido en persistencia
        pedidoRepository.save(pedido);
        
        if (!aprobado) {
            throw new RuntimeException("Pago rechazado por la pasarela automática");
        }
        
        return savedPago;
    }
    
    public Pago obtenerPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado para el pedido"));
    }
}
