package com.ejemplo.pedido.service;

import com.ejemplo.pedido.model.PedidoActual;
import com.ejemplo.pedido.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class PedidoActualService {
    private final PedidoActual pedido = new PedidoActual();

    public PedidoActual getPedido() {
        return pedido;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        pedido.agregar(producto, cantidad);
    }

    public void vaciar() {
        pedido.vaciar();
    }
}