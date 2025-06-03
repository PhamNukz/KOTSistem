package com.ejemplo.pedido.service;

import com.ejemplo.pedido.exceptions.ArqueoException;
import com.ejemplo.pedido.model.Arqueo;
import com.ejemplo.pedido.model.Pedido;
import com.ejemplo.pedido.repository.ArqueoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArqueoServiceImpl implements ArqueoService {
    @Autowired
    private ArqueoRepository arqueoRepository;


    @Override
    public Arqueo findById(Long id) {
        return this.arqueoRepository.findById(id).orElseThrow(
                () -> new ArqueoException("Arqueo no encontrado")
        );
    }

    @Override
    public List<Arqueo> findAll() {
        return this.arqueoRepository.findAll();
    }

    @Override
    public Arqueo save(Arqueo arqueo) {
        return this.arqueoRepository.save(arqueo);
    }

    //Sumar totales
    @Override
    public Double getTotalArqueo(Long idArqueo) {
        return arqueoRepository.sumTotalPedidosByArqueoId(idArqueo);
    }

    //Agregar pedido a un arqueo
    @Override
    public void agregarPedidoAArqueo(Long idArqueo, Pedido pedido) {
        Arqueo arqueo = arqueoRepository.findById(idArqueo)
                .orElseThrow(() -> new RuntimeException("Arqueo no encontrado"));
        arqueo.addPedido(pedido);
        arqueoRepository.save(arqueo);
    }

    public List<Pedido> BuscarPedidosAsociadosAUnArqueo(Long id){
        try {
            Optional<Arqueo> arqueo = this.arqueoRepository.findById(id);
            return this.arqueoRepository.BuscarPedidosAsociadosAUnArqueo(id);
        }catch (ArqueoException e){
            throw new ArqueoException("Arqueo no encontrado");
        }
    }
}