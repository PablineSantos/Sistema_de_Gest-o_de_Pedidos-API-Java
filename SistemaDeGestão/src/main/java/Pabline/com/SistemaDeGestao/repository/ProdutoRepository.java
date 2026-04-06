package Pabline.com.SistemaDeGestao.repository;

import Pabline.com.SistemaDeGestao.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
