package br.edu.ifsp.demo_clean.service;

import org.springframework.stereotype.Service;

import br.edu.ifsp.demo_clean.model.Carro;
import br.edu.ifsp.demo_clean.model.Cliente;
import br.edu.ifsp.demo_clean.model.Venda;
import br.edu.ifsp.demo_clean.repository.CarrosRepository;
import br.edu.ifsp.demo_clean.repository.ClienteRepository;
import br.edu.ifsp.demo_clean.repository.VendaRepository;

import java.util.*;

@Service
public class VendaService {

    private VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository){
        this.vendaRepository = vendaRepository;
    }

    //venda
    public void addVenda(Venda venda){vendaRepository.save(venda);}
    public List<Venda> listVendas(){return vendaRepository.findAll();}
}
