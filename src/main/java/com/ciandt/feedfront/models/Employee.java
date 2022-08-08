package com.ciandt.feedfront.models;

import com.ciandt.feedfront.exceptions.ComprimentoInvalidoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 3, message = "O nome deve ter mais de 2 caracteres")
    private String nome;
    @Size(min = 3, message = "O sobrenome deve ter mais de 2 caracteres")
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Já existe um cadastro com esse email")
    private String email;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private List<Feedback> feedbackFeitos;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id")
    private List<Feedback> feedbackRecebidos;

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        setNome(nome);
        setSobrenome(sobrenome);
        setEmail(email);
    }

}
