package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.EstoqueDTO;
import br.edu.ifsp.demo_clean.model.Estoque;
import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.repository.EstoqueRepository;
import br.edu.ifsp.demo_clean.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    public EstoqueRepository estoqueRepository;
    public LivroRepository livroRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, LivroRepository livroRepository) {
        this.estoqueRepository = estoqueRepository;
        this.livroRepository = livroRepository;
    }

    public List<Estoque> getAllDisponiveis() {
        return estoqueRepository.findByDisponibilidade(true);
    }

    public Estoque getById(int id) throws Exception {
        try {
            Estoque estoqueLocalizado = null;

            Optional<Estoque> estoque = estoqueRepository.findById(id);

            if (!estoque.isEmpty()) {
                estoqueLocalizado = estoque.get();
            }

            return estoqueLocalizado;
        } catch (Exception e) {
            throw new Exception("Não encontrado: ", e);
        }
    }

    public void addExemplar(EstoqueDTO estoqueDTO) {
        Livro livro = livroRepository.findByIsbn(estoqueDTO.isbn);

        if (livro == null) {
            throw new Error("ERRO: Não existe um livro com o ISBN informado!");
        }

        Estoque estoque = new Estoque(
                estoqueDTO.codigoExemplar,
                estoqueDTO.disponibilidade,
                livro
        );

        this.estoqueRepository.save(estoque);
    }

    public Estoque updateExemplar(Estoque estoque) throws Exception {
        try {
            Optional<Estoque> estoqueUpdate = estoqueRepository.findById(estoque.getCodigoExemplar());

            if (!estoqueUpdate.isEmpty()) {
                Estoque estoqueLocalizado = estoqueUpdate.get();
                estoqueLocalizado.setDisponibilidade(estoque.getDisponibilidade());

                return estoqueRepository.save(estoqueLocalizado);
            } else {
                throw new Exception("Exemplar não encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar a disponibilidade: ", e);
        }
    }

    public void deletarExemplar(int id) throws Exception {
        try {
            Estoque estoque = getById(id);

            if (estoque.getDisponibilidade()) {
                estoqueRepository.delete(estoque);
            } else {
                throw new Exception("Erro ao excluir o exemplar. Ele não está disponível no momento.");
            }
        } catch (Exception ex) {
            throw new Exception("Exemplar não encontrado");
        }

    }
}

