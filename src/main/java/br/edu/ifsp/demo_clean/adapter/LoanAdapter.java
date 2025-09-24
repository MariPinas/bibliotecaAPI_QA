package br.edu.ifsp.demo_clean.adapter;

import br.edu.ifsp.demo_clean.dto.response.LoanDTO;
import br.edu.ifsp.demo_clean.interfaces.DTOAdapter;
import br.edu.ifsp.demo_clean.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanAdapter implements DTOAdapter<LoanDTO, Loan> {

    private LoanDTO parse(Loan loan) {
        return new LoanDTO(loan.exemplar.livro.titulo, loan.exemplar.getCodigoExemplar(), loan.dataEmprestimo, loan.dataVencimento, loan.dataDevolucao);
    }

    @Override
    public LoanDTO toDTO(Loan value) {
        return parse(value);
    }

    @Override
    public List<LoanDTO> toDTOs(List<Loan> values) {
        return values.stream().map(this::parse).toList();
    }
}
