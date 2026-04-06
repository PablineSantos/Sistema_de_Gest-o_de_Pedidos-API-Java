package Pabline.com.SistemaDeGestao.service;

import Pabline.com.SistemaDeGestao.entity.Produto;
import Pabline.com.SistemaDeGestao.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;


@Service

public class ProdutoService {

    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;



    }
    public Produto cadastrarProduto (Produto produtoNovo){
        Produto produto = new Produto();
        produto.setNome(produtoNovo.getNome());
        produto.setPreco(produtoNovo.getPreco());
        produto.setEstoque(produtoNovo.getEstoque());
        return produtoRepository.save(produto);
    }

    public Produto produtoPorId(Long id){
        if (id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id Nulo.");
        }
        return produtoRepository.findById(id).orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado Produto com esse id "+id));
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto atualizarProduto(Long id ,Produto produtoAtualizado){
        Produto produto = produtoPorId(id);
        if(produtoAtualizado.getNome() != null && !produtoAtualizado.getNome().isBlank()){
            produto.setNome(produtoAtualizado.getNome());
        }
        if(produtoAtualizado.getPreco() != null ){
            produto.setPreco(produtoAtualizado.getPreco());
        }
        if(produtoAtualizado.getEstoque() != null ){
            produto.setEstoque(produtoAtualizado.getEstoque());
        }
        return produtoRepository.save(produto);
    }

    public void DeletarProduto(Long id){
        Produto produto =produtoPorId(id);
        produtoRepository.delete(produto);
    }


}
