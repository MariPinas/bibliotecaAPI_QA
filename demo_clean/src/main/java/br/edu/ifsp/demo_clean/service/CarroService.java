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
public class CarroService {

    private CarrosRepository carrosRepository;

    public CarroService(CarrosRepository carrosRepository){
        this.carrosRepository = carrosRepository;
    }

    //carro
    public void addCarro(Carro carro){carrosRepository.save(carro);}
    public List<Carro> listarCarros(){return carrosRepository.findAll();}

}
