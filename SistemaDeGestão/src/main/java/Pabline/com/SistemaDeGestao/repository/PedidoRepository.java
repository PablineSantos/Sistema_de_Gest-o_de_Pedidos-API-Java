package Pabline.com.SistemaDeGestao.repository;

import Pabline.com.SistemaDeGestao.entity.Cliente;
import Pabline.com.SistemaDeGestao.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}