package br.edu.ifsp.demo_clean.controller;

import org.springframework.web.bind.annotation.*;

import br.edu.ifsp.demo_clean.model.Carro;
import br.edu.ifsp.demo_clean.model.Cliente;
import br.edu.ifsp.demo_clean.model.Venda;
import br.edu.ifsp.demo_clean.service.ServicoBaguncado;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Consumer ", description = "Responsável por controlar as replicações das tabelas")

public class Ccontroller {

    private ServicoBaguncado s;

    public Ccontroller(ServicoBaguncado s){this.s=s;}

    @PostMapping("/cli")
    public String salvaCli(@RequestBody Cliente c){s.addCli(c); return "ok cliente";}

    @GetMapping("/cli")
    public List<Cliente> todosCli(){return s.tdsCli();}

    @PostMapping("/carro")
    public String salvaCarro(@RequestBody Carro c){s.addCar(c);return "ok carro";}

    @GetMapping("/carro")
    public List<Carro> todosCar(){return s.tudoCarro();}

    @PostMapping("/venda")
    public String salvaVenda(@RequestBody Venda v){s.addV(v); return "ok venda";}

    @GetMapping("/venda")
    public List<Venda> todasV(){return s.vnd();}
}