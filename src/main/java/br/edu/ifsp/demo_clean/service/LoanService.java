package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.Result;
import br.edu.ifsp.demo_clean.model.Loan;
import br.edu.ifsp.demo_clean.model.Estoque;
import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.model.enums.UserCategory;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.repository.EmprestimoRepository;
import br.edu.ifsp.demo_clean.repository.EstoqueRepository;
import br.edu.ifsp.demo_clean.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final EmprestimoRepository emprestimoRepository;
    private final UserRepository userRepository;
    private final EstoqueRepository estoqueRepository;

    public LoanService(EmprestimoRepository emprestimoRepository, UserRepository userRepository, EstoqueRepository estoqueRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.userRepository = userRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public Result<Loan> register(int exemplaryCode, String cpf) {
        final Optional<Estoque> exemplary = estoqueRepository.findById(exemplaryCode);
        if (exemplary.isEmpty()) {
            return Result.failure("Exemplar não encontrado!");
        }

        final Optional<User> user = userRepository.findByCpf(cpf);
        if (user.isEmpty()) {
            return Result.failure("Usuário não encontrado!");
        }

        if (!validateUser(user.get())) {
            return Result.failure("Usuário inválido");
        }

        if (!validaExemplar(exemplary.get())) {
            return Result.failure("Exemplar não disponível");
        }

        final LocalDate dueDate = calculateDueDate(user.get(), exemplary.get().livro);
        exemplary.get().setDisponibilidade(false);
        final Loan loan = new Loan(user.get(), exemplary.get(), LocalDate.now(), dueDate, null);

        emprestimoRepository.save(loan);
        estoqueRepository.save(exemplary.get());

        return Result.success(loan);
    }

    private boolean validateUser(User usuario) {
        final boolean userIsActive = usuario.getStatus().equals(UserStatus.ATIVO);
        final boolean copyAvailable = usuario.allActiveLoans() < usuario.getCategory().maximoLivrosEmprestados();
        return userIsActive && copyAvailable;
    }

    private boolean validaExemplar(Estoque exemplar) {
        return exemplar.getDisponibilidade();
    }

    private LocalDate calculateDueDate(User user, Livro book) {
        int days = user.getCategory().tempoEmprestimo();

        if(user.getCategory().equals(UserCategory.ALUNO) && user.getCourse().livroRelacionado(book.categoria)) {
            days = 30;
        }

        return LocalDate.now().plusDays(days);
    }

    public Result<Loan> devolution(int loanId) {
        final Optional<Loan> loan = emprestimoRepository.findById(loanId);

        if(loan.isEmpty()) {
            return Result.failure("Empréstimo não encontrado!");
        }

        if(loan.get().emprestimoFinalizado()) {
            return Result.failure("Devolução já realizada!");
        }

        loan.get().dataDevolucao = LocalDate.now();
        loan.get().exemplar.setDisponibilidade(true);
        emprestimoRepository.save(loan.get());
        estoqueRepository.save(loan.get().exemplar);

        return Result.success(loan.get());
    }

    public List<Loan> listAssets() {
        return emprestimoRepository.findAllByDataDevolucaoIsNull();
    }

    public List<Loan> listHistory() {
        return emprestimoRepository.findAll();
    }
}
