package com.ejemplo.pedido.repository;

import com.ejemplo.pedido.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}