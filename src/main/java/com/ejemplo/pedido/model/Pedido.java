package com.ejemplo.pedido.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private BigDecimal total;
    private String cliente;
    private String producto;
    private LocalDateTime fecha;


    public Pedido() {
        this.fecha = LocalDateTime.now();
    }

    public Pedido(String cliente, String producto) {
        this.cliente = cliente;
        this.producto = producto;
        this.fecha = LocalDateTime.now();
    }


    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public String getProducto() { return producto; }
    public LocalDateTime getFecha() { return fecha; }

    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setProducto(String producto) { this.producto = producto; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }


    @ManyToOne
    @JoinColumn(name = "id_arqueo")
    private Arqueo arqueo;  // Relación ManyToOne

    public Arqueo getArqueo() {
        return arqueo;
    }

    public void setArqueo(Arqueo arqueo) {
        this.arqueo = arqueo;
    }


}