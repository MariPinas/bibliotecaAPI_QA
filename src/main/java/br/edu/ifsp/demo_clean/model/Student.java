package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.*;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    @Enumerated(EnumType.STRING)
    private Course course;

    public Student() {
    }

    public Student(String name, String cpf, String email, Course course) {
        super(name, cpf, email);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public LoanStrategy getLoanStrategy() {
        final int maxBooks = 3;
        final int loanDays = 15;

        return new StudentLoanStrategy(new LoanPolicy(maxBooks, loanDays));
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nome='" + getName() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", curso=" + course +
                ", status=" + getStatus() +
                '}';
    }
}
