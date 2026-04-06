package Pabline.com.SistemaDeGestao.controller;

import Pabline.com.SistemaDeGestao.entity.Pedido;
import Pabline.com.SistemaDeGestao.enums.Status;
import Pabline.com.SistemaDeGestao.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido){
        return ResponseEntity.ok(pedidoService.adicionarPedido(pedido));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar(){
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.pedidoPorId(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestParam Status status){
        return ResponseEntity.ok(pedidoService.atualizarStatus(id,status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}