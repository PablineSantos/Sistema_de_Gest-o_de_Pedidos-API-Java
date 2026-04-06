package Pabline.com.SistemaDeGestao.service;

import Pabline.com.SistemaDeGestao.entity.Cliente;
import Pabline.com.SistemaDeGestao.entity.Endereco;
import Pabline.com.SistemaDeGestao.entity.Produto;
import Pabline.com.SistemaDeGestao.repository.ClienteRepository;
import Pabline.com.SistemaDeGestao.repository.EnderecoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;

@Service
public class ClienteService {

    public final ClienteRepository clienteRepository;
    public final EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Cliente cadastrarCliente(Cliente clienteNovo){
        Cliente cliente =new Cliente();
        if (clienteRepository.existsByEmail(clienteNovo.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        cliente.setNome(clienteNovo.getNome());
        cliente.setEmail(clienteNovo.getEmail());
        if (clienteNovo.getEnderecos() != null) {
            for (Endereco enderecoNovo : clienteNovo.getEnderecos()) {
                Endereco endereco = new Endereco();
                endereco.setRua(enderecoNovo.getRua());
                endereco.setCidade(enderecoNovo.getCidade());
                endereco.setCep(enderecoNovo.getCep());

                cliente.addEndereco(endereco);
            }
        }
        return clienteRepository.save(cliente);
    }

    public Cliente clientePorId (Long id){
        if (id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id nulo");
        }
        return clienteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente com id "+id+" não foi encontrado"));
    }

    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente ( long id,Cliente clienteAtualizar){
        Cliente cliente = clientePorId(id);
        if (clienteAtualizar.getNome() != null && !clienteAtualizar.getNome().isBlank()){
            cliente.setNome(clienteAtualizar.getNome());
        }
        if (clienteAtualizar.getEmail() != null && !clienteAtualizar.getEmail().isBlank()) {
            if (clienteRepository.existsByEmail(clienteAtualizar.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
            cliente.setEmail(clienteAtualizar.getEmail());
        }
        if (clienteAtualizar.getEnderecos() != null){

            for (Endereco endereco : clienteAtualizar.getEnderecos()) {

                Endereco enderecos = enderecoRepository.findById(endereco.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));

                if (!enderecos.getCliente().getId().equals(cliente.getId())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço não pertence ao cliente");
                }

                if (endereco.getRua() != null && !endereco.getRua().isBlank()){
                    enderecos.setRua(endereco.getRua());
                }
                if (endereco.getCidade() != null && !endereco.getCidade().isBlank()){
                    enderecos.setCidade(endereco.getCidade());
                }
                if (endereco.getCep() != null && !endereco.getCep().isBlank()){
                    enderecos.setCep(endereco.getCep());
                }
            }

        }
        return clienteRepository.save(cliente);
    }

    public void deletarCliente (Long id){
        Cliente cliente=clientePorId(id);
        clienteRepository.delete(cliente);
    }

}
