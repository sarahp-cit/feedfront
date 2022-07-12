package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.arquivo.Arquivo;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public class Employee {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static final String arquivoCriado = "src/main/resources/arquivo.txt";

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public Employee(String id, String nome, String sobrenome, String email) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public static boolean salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        Arquivo.inserirLinhaArquivo(arquivoCriado, EmployeeBuilder.employeeToArquivo(employee));
        return true;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        Arquivo.alterarLinhaArquivo(arquivoCriado, employee.id, EmployeeBuilder.employeeToArquivo(employee));
        return employee;
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        List<String> employees = Arquivo.lerArquivo(arquivoCriado);
        return EmployeeBuilder.arquivoToEmployees(employees);
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return EmployeeBuilder.arquivoToEmployee(Arquivo.buscarPorIdArquivo(arquivoCriado, id));
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        Arquivo.deletarLinhaArquivo(arquivoCriado, id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
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

}
