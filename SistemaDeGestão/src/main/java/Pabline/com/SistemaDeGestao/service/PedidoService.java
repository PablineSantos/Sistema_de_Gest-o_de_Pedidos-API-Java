package Pabline.com.SistemaDeGestao.service;

import Pabline.com.SistemaDeGestao.entity.*;
import Pabline.com.SistemaDeGestao.enums.Status;
import Pabline.com.SistemaDeGestao.repository.PedidoRepository;
import Pabline.com.SistemaDeGestao.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoRepository = produtoRepository;
    }


    public Pedido adicionarPedido (Pedido pedidoNovo){

        Pedido pedido = new Pedido();
        pedido.setDate(LocalDate.now());
        pedido.setStatus(Status.CRIADO);

        Cliente cliente = clienteService.clientePorId(pedidoNovo.getCliente().getId());
        pedido.setCliente(cliente);

        Endereco enderecoSelecionado = cliente.getEnderecos()
                .stream()
                .filter(e -> e.getId().equals(pedidoNovo.getEndereco().getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Endereço não pertence ao cliente"));

        pedido.setEndereco(enderecoSelecionado);

        double total = 0;

        for (ItemPedido itemPedido : pedidoNovo.getItemPedidos()) {

            Produto produto = produtoRepository.findById(itemPedido.getProduto().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            if (produto.getEstoque() < itemPedido.getQuantidade()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente");
            }

            produto.setEstoque(produto.getEstoque() - itemPedido.getQuantidade());

            ItemPedido novoItem = new ItemPedido();
            novoItem.setProduto(produto);
            novoItem.setQuantidade(itemPedido.getQuantidade());
            novoItem.setPrecoUnitario(produto.getPreco());

            pedido.addItemPedido(novoItem);

            total += itemPedido.getQuantidade() * produto.getPreco();
        }

        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public Pedido pedidoPorId(Long id){
        Pedido pedido= pedidoRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido nao encontrado"));
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
    public void deletarPedido(Long id){
        Pedido pedido = pedidoPorId(id);

        if (pedido.getStatus() != Status.CRIADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Só pedidos CRIADOS podem ser cancelados");
        }

        pedido.setStatus(Status.CANCELADO);
        pedidoRepository.save(pedido);
    }
    public Pedido atualizarStatus(Status status,Long id){
        Pedido pedido= pedidoPorId(id);
        if (pedido.getStatus() == Status.CRIADO && status == Status.PAGO) {
            pedido.setStatus(status);
        }
        else if (pedido.getStatus() == Status.CRIADO && status == Status.CANCELADO) {
            pedido.setStatus(status);
        }
        else if (pedido.getStatus() == Status.PAGO && status == Status.ENVIADO) {
            pedido.setStatus(status);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transição de status inválida");
        }
        return pedidoRepository.save(pedido);
    }
}
