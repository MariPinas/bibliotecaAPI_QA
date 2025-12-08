package br.mapper;

import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.model.Loan;

public class LoanMapper {
    public static LoanResponseDTO toDTO(Loan loan) {
        return new LoanResponseDTO(
                loan.getUser(),
                loan.getStock(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getDevolutionDate());
    }
}
