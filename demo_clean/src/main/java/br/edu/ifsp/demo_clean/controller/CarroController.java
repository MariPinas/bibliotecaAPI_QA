package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.model.Carro;
import br.edu.ifsp.demo_clean.model.Cliente;
import br.edu.ifsp.demo_clean.model.Venda;
import br.edu.ifsp.demo_clean.service.CarroService;
import br.edu.ifsp.demo_clean.service.VendaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Consumer ", description = "Responsável por controlar as replicações das tabelas")

public class CarroController {

    private CarroService carroService;

    public CarroController(CarroService carroService){this.carroService=carroService;}

    @PostMapping("/carro")
    public String salvaCarro(@RequestBody Carro carro){carroService.addCarro(carro); return "ok carro";}

    @GetMapping("/carro")
    public List<Carro> listarCarros(){return carroService.listarCarros();}
}