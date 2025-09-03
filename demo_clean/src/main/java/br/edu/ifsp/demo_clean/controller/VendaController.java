package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.model.Carro;
import br.edu.ifsp.demo_clean.model.Cliente;
import br.edu.ifsp.demo_clean.model.Venda;
import br.edu.ifsp.demo_clean.service.VendaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Consumer ", description = "Responsável por controlar as replicações das tabelas")

public class VendaController {

    private VendaService vendaService;

    public VendaController(VendaService vendaService){this.vendaService=vendaService;}


    @PostMapping("/venda")
    public String salvaVenda(@RequestBody Venda venda){vendaService.addVenda(venda); return "ok venda";}

    @GetMapping("/venda")
    public List<Venda> todasVendas(){return vendaService.listVendas();}
}