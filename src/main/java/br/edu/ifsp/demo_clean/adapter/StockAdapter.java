package br.edu.ifsp.demo_clean.adapter;

import br.edu.ifsp.demo_clean.dto.response.StockDTO;
import br.edu.ifsp.demo_clean.interfaces.DTOAdapter;
import br.edu.ifsp.demo_clean.model.Estoque;

import java.util.List;

public class StockAdapter implements DTOAdapter<StockDTO, Estoque> {

    private StockDTO parse(Estoque stock) {
        return new StockDTO(stock.livro.titulo, stock.livro.isbn, stock.getCodigoExemplar(), stock.getDisponibilidade());
    }

    @Override
    public StockDTO toDTO(Estoque value) {
        return parse(value);
    }

    @Override
    public List<StockDTO> toDTOs(List<Estoque> values) {
        return values.stream().map(this::parse).toList();
    }
}
