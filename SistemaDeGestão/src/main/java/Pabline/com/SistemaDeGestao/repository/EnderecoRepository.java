package Pabline.com.SistemaDeGestao.repository;

import Pabline.com.SistemaDeGestao.entity.Cliente;
import Pabline.com.SistemaDeGestao.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}