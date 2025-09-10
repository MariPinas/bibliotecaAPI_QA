package br.edu.ifsp.demo_clean.adapter;

import br.edu.ifsp.demo_clean.dto.response.LoanDTO;
import br.edu.ifsp.demo_clean.interfaces.DTOAdapter;
import br.edu.ifsp.demo_clean.model.Emprestimo;

import java.util.List;

public class LoanAdapter implements DTOAdapter<LoanDTO, Emprestimo> {

    private LoanDTO parse(Emprestimo loan) {
        return new LoanDTO(loan.exemplar.livro.titulo, loan.exemplar.getCodigoExemplar(), loan.dataEmprestimo, loan.dataVencimento, loan.dataDevolucao);
    }

    @Override
    public LoanDTO toDTO(Emprestimo value) {
        return parse(value);
    }

    @Override
    public List<LoanDTO> toDTOs(List<Emprestimo> values) {
        return values.stream().map(this::parse).toList();
    }
}
