package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.Carro;
import br.edu.ifsp.demo_clean.model.Cliente;
import br.edu.ifsp.demo_clean.model.Venda;
import br.edu.ifsp.demo_clean.repository.CarrosRepository;
import br.edu.ifsp.demo_clean.repository.ClienteRepository;
import br.edu.ifsp.demo_clean.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository=clienteRepository;
    }

    //cliente
    public void addCliente(Cliente c){clienteRepository.save(c);}
    public List<Cliente> todosClientes(){return clienteRepository.findAll();}

}
