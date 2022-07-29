package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private List<Feedback> feedbackFeitos;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id")
    private List<Feedback> feedbackRecebidos;

    public Employee() {
    }

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        setNome(nome);
        setSobrenome(sobrenome);
        setEmail(email);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        if (nome.length() <= 2)
            throw new ComprimentoInvalidoException("Comprimento do nome deve ser maior que 2 caracteres.");
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        if (sobrenome.length() <= 2)
            throw new ComprimentoInvalidoException("Comprimento do sobrenome deve ser maior que 2 caracteres.");
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Feedback> getFeedbackFeitos() {
        return feedbackFeitos;
    }

    public List<Feedback> getFeedbackRecebidos() {
        return feedbackRecebidos;
    }

    public void setFeedbackFeitos(List<Feedback> feedbackFeitos) {
        this.feedbackFeitos = feedbackFeitos;
    }

    public void setFeedbackRecebidos(List<Feedback> feedbackRecebidos) {
        this.feedbackRecebidos = feedbackRecebidos;
    }

    @Override
    public String toString() {
        return "\nid='" + id + "'" +
                ", nome='" + nome + "'" +
                ", sobrenome='" + sobrenome + "'" +
                ", email='" + email + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!Objects.equals(id, employee.id)) return false;
        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
