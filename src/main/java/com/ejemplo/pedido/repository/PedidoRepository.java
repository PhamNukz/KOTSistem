package com.ejemplo.pedido.repository;

import com.ejemplo.pedido.model.Arqueo;
import com.ejemplo.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByArqueoIsNull();
    List<Pedido> findByArqueoId(Long id);


    @Modifying
    @Query("UPDATE Pedido p SET p.arqueo = :arqueo WHERE p IN :pedidos")
    void updateArqueoForPedidos(@Param("arqueo") Arqueo arqueo, @Param("pedidos") List<Pedido> pedidos);
}
