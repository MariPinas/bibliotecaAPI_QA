package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.Loan;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.repository.LoanRepository;
import br.edu.ifsp.demo_clean.repository.StockRepository;
import br.edu.ifsp.demo_clean.repository.UserRepository;
import br.edu.ifsp.demo_clean.strategy.LoanPolicy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, StockRepository stockRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
    }

    public Loan register(int stockCode, String cpf) {
        final Optional<Stock> exemplary = stockRepository.findById(stockCode);
        if (exemplary.isEmpty()) {
            throw new Error("Exemplar não encontrado!");
        }

        final Optional<User> user = userRepository.findByCpf(cpf);
        if (user.isEmpty()) {
            throw new Error("Usuário não encontrado!");
        }

        if (!validateUser(user.get())) {
            throw new Error("Usuário inválido");
        }

        if (!validaExemplar(exemplary.get())) {
            throw new Error("Exemplar não disponível");
        }

        final LocalDate dueDate = calculateDueDate(user.get(), exemplary.get().book);
        exemplary.get().setAvailability(false);
        final Loan loan = new Loan(user.get(), exemplary.get(), LocalDate.now(), dueDate, null);

        loanRepository.save(loan);
        stockRepository.save(exemplary.get());

        return loan;
    }

    private boolean validateUser(User usuario) {
        // temporariamente comentei a verificacao de status que chamava usuario.getStatus(),
        // porque como eu mudei a classe user, nao tava conseguindo rodar pra testar oq eu fiz
        // final boolean userIsActive = usuario.getStatus().equals(UserStatus.ATIVO);
        LoanPolicy policy = usuario.getLoanStrategy().getPolicy();

        final boolean copyAvailable = usuario.allActiveLoans() < policy.getMaxBooks();
        // retornando apenas a verificacao de limite
        return copyAvailable;
    }

    private boolean validaExemplar(Stock exemplar) {
        return exemplar.isAvailability();
    }

    private LocalDate calculateDueDate(User user, Book book) {
        LoanPolicy policy = user.getLoanStrategy().getPolicy();

        int days = policy.getLoanDays();

        // comentado temporariamente
        // if(user.getCategory().equals(UserCategory.ALUNO) && user.getCourse().livroRelacionado(book.categoria)) {
        //     days = 30;
        // }

        return LocalDate.now().plusDays(days);
    }

    public Loan devolution(int loanId) {
        final Optional<Loan> loan = loanRepository.findById(loanId);

        if(loan.isEmpty()) {
            throw new Error("Empréstimo não encontrado!");
        }

        if(loan.get().isCompleted()) {
            throw new Error("Devolução já realizada!");
        }

        loan.get().setDevolutionDate(LocalDate.now());
        loan.get().getStock().setAvailability(true);
        loanRepository.save(loan.get());
        stockRepository.save(loan.get().getStock());

        return loan.get();
    }

    public List<Loan> listAssets() {
        return loanRepository.findAllByDevolutionDateIsNull();
    }

    public List<Loan> listHistory() {
        return loanRepository.findAll();
    }
}
