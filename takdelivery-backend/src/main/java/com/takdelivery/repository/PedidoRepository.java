// repository/PedidoRepository.java
package com.takdelivery.repository;

import com.takdelivery.model.enums.EstadoPedido;
import com.takdelivery.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByRepartidorId(Long repartidorId);

    // ✅ NUEVO: consulta directa a BD sin cargar en memoria
    boolean existsByClienteIdAndEstado(Long clienteId, EstadoPedido estado);
}