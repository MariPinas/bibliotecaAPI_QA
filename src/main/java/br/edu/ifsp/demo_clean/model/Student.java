package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.factory.UserRegistry;
import br.edu.ifsp.demo_clean.model.enums.*;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    static {
        UserRegistry.register(Student.class, dto ->
                new Student(dto.name, dto.cpf, dto.email, dto.course, dto.status)
        );
    }

    @Enumerated(EnumType.STRING)
    private Course course;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public Student() { super(); }

    public Student(String name, String cpf, String email, Course course, UserStatus status) {
        super(name, cpf, email, course, status);
    }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    @Override
    public LoanStrategy<?> getLoanStrategy() {
        var maxBooks = 3;
        var loanDays = 15;
        LoanPolicy policy = new LoanPolicy(maxBooks, loanDays);
        return new StudentLoanStrategy(policy);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nome='" + getName() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", curso=" + course +
                ", status=" + status +
                '}';
    }
}
