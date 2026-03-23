package com.takdelivery.service;

import com.takdelivery.model.Producto;
import com.takdelivery.model.Tienda;
import com.takdelivery.repository.ProductoRepository;
import com.takdelivery.repository.TiendaRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final TiendaRepository tiendaRepository;

    public List<Producto> obtenerPorTienda(Long tiendaId) {
        return productoRepository.findByTiendaId(tiendaId);
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto crear(Producto producto) {
        if (producto.getTienda() == null || producto.getTienda().getId() == null) {
            throw new RuntimeException("La tienda es requerida para el producto");
        }
        Tienda tienda = tiendaRepository.findById(producto.getTienda().getId())
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        producto.setTienda(tienda);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto actualizacion) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(actualizacion.getNombre());
        producto.setDescripcion(actualizacion.getDescripcion());
        producto.setPeso(actualizacion.getPeso());
        producto.setPrecio(actualizacion.getPrecio());
        producto.setPrecioPromocion(actualizacion.getPrecioPromocion());
        producto.setImagen(actualizacion.getImagen());
        producto.setActivo(actualizacion.isActivo());
        producto.setStockDisponible(actualizacion.getStockDisponible());
        return productoRepository.save(producto);
    }
}
