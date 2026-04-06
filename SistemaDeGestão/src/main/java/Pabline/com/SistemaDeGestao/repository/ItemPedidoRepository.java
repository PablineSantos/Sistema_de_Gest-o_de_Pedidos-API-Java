package Pabline.com.SistemaDeGestao.repository;

import Pabline.com.SistemaDeGestao.entity.Cliente;
import Pabline.com.SistemaDeGestao.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {
}