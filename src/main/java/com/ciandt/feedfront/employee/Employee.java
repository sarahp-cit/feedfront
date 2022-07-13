package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.arquivo.Arquivo;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Employee implements Serializable {
    private final String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static final String arquivoCriado = "src/main/resources/";

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        setNome(nome);
        setSobrenome(sobrenome);
        this.email = email;
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        List<Employee> employees = listarEmployees();
        for (Employee employ : employees) {
            if (employ.equals(employee)) {
                throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
            }
        }
        Arquivo.criarArquivoEmployee(arquivoCriado, employee);
        return employee;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException {
        buscarEmployee(employee.getId());
        Arquivo.criarArquivoEmployee(arquivoCriado, employee);
        return employee;
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        return Arquivo.buscarTodosEmployeesArquivo(arquivoCriado);
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return Arquivo.buscarEmployeePorIdArquivo(arquivoCriado + id + ".byte");
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        buscarEmployee(id);
        Arquivo.deletarLinhaArquivo(arquivoCriado + id + ".byte");
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

    public void setEmail(String email) throws EmailInvalidoException {

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
