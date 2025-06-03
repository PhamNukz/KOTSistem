package com.ejemplo.pedido.service;

import com.ejemplo.pedido.model.Arqueo;
import com.ejemplo.pedido.model.Pedido;

import java.util.List;

public interface ArqueoService {

    Arqueo findById(Long id);
    List<Arqueo> findAll();
    Arqueo save(Arqueo arqueo);
    Double getTotalArqueo(Long idArqueo);
    void agregarPedidoAArqueo(Long idArqueo, Pedido pedido);

}
