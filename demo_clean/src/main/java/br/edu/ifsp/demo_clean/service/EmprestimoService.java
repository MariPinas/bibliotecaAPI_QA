package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.Emprestimo;
import br.edu.ifsp.demo_clean.model.Estoque;
import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.model.enums.CategoriaUsuario;
import br.edu.ifsp.demo_clean.model.enums.StatusUsuario;
import br.edu.ifsp.demo_clean.repository.EmprestimoRepository;
import br.edu.ifsp.demo_clean.repository.EstoqueRepository;
import br.edu.ifsp.demo_clean.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {
    private EmprestimoRepository emprestimoRepository;
    private UsuarioRepository usuarioRepository;
    private EstoqueRepository estoqueRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, UsuarioRepository usuarioRepository, EstoqueRepository estoqueRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public String addEmprestimo(int codigoExemplar, String cpf) {
        final Optional<Estoque> exemplar = estoqueRepository.findById(codigoExemplar);
        if (exemplar.isEmpty()) {
            return "Exemplar não encontrado!";
        }

        final Optional<Usuario> usuario = usuarioRepository.findByCpf(cpf);
        if (usuario.isEmpty()) {
            return "Usuário não encontrado!";
        }

        if (!validaUsuario(usuario.get())) {
            return "Usuário inválido";
        }

        if (!validaExemplar(exemplar.get())) {
            return "Exemplar não disponível";
        }

        final LocalDate vencimento = calculaVencimento(usuario.get(), exemplar.get().livro);
        exemplar.get().setDisponibilidade(false);
        final Emprestimo emprestimo = new Emprestimo(usuario.get(), exemplar.get(), LocalDate.now(), vencimento, null);

        emprestimoRepository.save(emprestimo);
        estoqueRepository.save(exemplar.get());

        return "ok empréstimo";
    }

    private boolean validaUsuario(Usuario usuario) {
        final boolean usuarioAtivo = usuario.getStatus().equals(StatusUsuario.ATIVO);
        final boolean limiteDeEmprestimoDisponivel = usuario.totalEmprestimosAtivos() < usuario.getCategoria().maximoLivrosEmprestados();
        return usuarioAtivo && limiteDeEmprestimoDisponivel;
    }

    private boolean validaExemplar(Estoque exemplar) {
        return exemplar.getDisponibilidade();
    }

    private LocalDate calculaVencimento(Usuario usuario, Livro livro) {
        int dias = usuario.getCategoria().tempoEmprestimo();

        if(usuario.getCategoria().equals(CategoriaUsuario.ALUNO) && livro.categoria.equals(usuario.getCategoria()) ) {
            dias = 30;
        }

        return LocalDate.now().plusDays(dias);
    }

    public String devolucaoEmprestimo(int idEmprestimo) {
        final Optional<Emprestimo> emprestimo = emprestimoRepository.findById(idEmprestimo);

        if(emprestimo.isEmpty()) {
            return "Empréstimo não encontrado!";
        }

        if(emprestimo.get().emprestimoFinalizado()) {
            return "Devolução já realizada!";
        }

        emprestimo.get().dataDevolucao = LocalDate.now();
        emprestimoRepository.save(emprestimo.get());
        return "Devolução cadastrada com sucesso!";
    }

    public List<Emprestimo> listaAtivos() {
        return emprestimoRepository.findAllByDataDevolucaoIsNull();
    }

    public List<Emprestimo> listaHistorico() {
        return emprestimoRepository.findAll();
    }
}
