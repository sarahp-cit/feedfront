package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Employee implements Serializable {
    private final String id;
    private String nome;
    private String sobrenome;
    private String email;
    private String arquivo;
    
    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        setArquivo(getId() + ".byte");
        setNome(nome);
        setSobrenome(sobrenome);
        setEmail(email);
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
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

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
