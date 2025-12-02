package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.*;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @Enumerated(EnumType.STRING)
    private Course course;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public Student() { super(); }

    public Student(String name, String cpf, String email, Course course, UserStatus status) {
        super(name, cpf, email, UserCategory.STUDENT);
        this.course = course;
        this.status = status;
    }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    @Override
    public LoanStrategy<?> getLoanStrategy() {
        LoanPolicy policy = new LoanPolicy(getCategory().getMaximumBooksBorrowed(), getCategory().getLoanTime());
        return new StudentLoanStrategy(policy);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nome='" + getName() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", categoria=" + getCategory() +
                ", curso=" + course +
                ", status=" + status +
                '}';
    }
}
