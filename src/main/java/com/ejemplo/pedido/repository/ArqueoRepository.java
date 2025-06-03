package com.ejemplo.pedido.repository;

import com.ejemplo.pedido.model.Arqueo;
import com.ejemplo.pedido.model.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArqueoRepository extends JpaRepository<Arqueo, Long> {

    // Suma los totales de todos los pedidos de un arqueo
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.arqueo.idArqueo = :idArqueo")
    Double sumTotalPedidosByArqueoId(@Param("idArqueo") Long idArqueo);

    // Alternativa: Trae el arqueo con sus pedidos ya cargados (evita LazyInitializationException)
    @Query("SELECT a FROM Arqueo a LEFT JOIN FETCH a.pedidos WHERE a.id = :id")
    List<Pedido> BuscarPedidosAsociadosAUnArqueo(Long id);


    @Query("SELECT MAX(a.idArqueo) FROM Arqueo a")
    Long getMaxIdArqueo();


}