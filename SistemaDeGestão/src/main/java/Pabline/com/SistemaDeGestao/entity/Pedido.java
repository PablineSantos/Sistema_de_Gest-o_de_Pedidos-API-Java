package Pabline.com.SistemaDeGestao.entity;

import Pabline.com.SistemaDeGestao.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Double total;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "cliente_id")
    @JsonBackReference ("cliente-pedido")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    @JsonManagedReference
    private Endereco endereco;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("pedido-item")
    private List<ItemPedido> itemPedidos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(LocalDate date, Endereco endereco, Status status, Cliente cliente) {
        this.date = date;
        this.endereco = endereco;
        this.status = status;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void addItemPedido(ItemPedido itemPedido){
        this.itemPedidos.add(itemPedido);
        itemPedido.setPedido(this);
    }

    public void removeItemPedido(ItemPedido itemPedido){
        this.itemPedidos.remove(itemPedido);
        itemPedido.setPedido(null);
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
