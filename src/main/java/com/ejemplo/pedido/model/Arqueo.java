package com.ejemplo.pedido.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;


@Entity
public class Arqueo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long idArqueo;  // ID grupal (ej: 101, 102...)
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @OneToMany(mappedBy = "arqueo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    // Método helper para agregar pedidos
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setArqueo(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdArqueo() {
        return idArqueo;
    }

    public void setIdArqueo(Long idArqueo) {
        this.idArqueo = idArqueo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}




