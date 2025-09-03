package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import br.edu.ifsp.demo_clean.model.Cliente;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Consumer ", description = "Responsável por controlar as replicações das tabelas")

public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){this.clienteService=clienteService;}

    @PostMapping("/cliente")
    public String salvaCli(@RequestBody Cliente c){clienteService.addCliente(c); return "ok cliente";}

    @GetMapping("/cliente")
    public List<Cliente> todosCli(){return clienteService.todosClientes();}

}