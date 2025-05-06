package com.ejemplo.pedido.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PedidoActual {
    private List<ProductoCantidad> productos = new ArrayList<>();


    public void agregar(Producto producto, int cantidad) {
        for (ProductoCantidad pc : productos) {
            if (pc.getProducto().getId().equals(producto.getId())) {
                pc.setCantidad(pc.getCantidad() + cantidad);
                return;
            }
        }
        productos.add(new ProductoCantidad(producto, cantidad));
    }

    public BigDecimal getTotal() {
        return productos.stream()
                .map(pc -> pc.getProducto().getPrecio().multiply(BigDecimal.valueOf(pc.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<ProductoCantidad> getProductos() {
        return productos;
    }

    public void vaciar() {
        productos.clear();
    }
}

