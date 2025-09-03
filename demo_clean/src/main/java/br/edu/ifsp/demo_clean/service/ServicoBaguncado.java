package br.edu.ifsp.demo_clean.service;

import org.springframework.stereotype.Service;

import br.edu.ifsp.demo_clean.model.Carro;
import br.edu.ifsp.demo_clean.model.Cliente;
import br.edu.ifsp.demo_clean.model.Venda;
import br.edu.ifsp.demo_clean.repository.Repos;
import br.edu.ifsp.demo_clean.repository.ReposCliente;
import br.edu.ifsp.demo_clean.repository.ReposVenda;

import java.util.*;

@Service
public class ServicoBaguncado {

    private Repos r;
    private ReposCliente rc;
    private ReposVenda rv;

    public ServicoBaguncado(Repos x, ReposCliente y, ReposVenda z){
        r=x; rc=y; rv=z;
    }

    //cliente
    public void addCli(Cliente c){rc.save(c);}
    public List<Cliente> tdsCli(){return rc.findAll();}

    //carro
    public void addCar(Carro c){r.save(c);}
    public List<Carro> tudoCarro(){return r.findAll();}

    //venda
    public void addV(Venda v){rv.save(v);}
    public List<Venda> vnd(){return rv.findAll();}
}
