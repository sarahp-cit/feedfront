package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest {
    private Employee employee;
    private DAO<Employee> employeeDAO;

    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException {
        Files.walk(Paths.get("src/main/resources/data/employee/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> new File(p.toString()).delete());

        employeeDAO = new EmployeeDAO();
        employee = new Employee("João", "Silveira", "j.silveira@email.com");

        employeeDAO.salvar(employee);
    }

    @Test
    public void listar() throws IOException {
        List<Employee> result = employeeDAO.listar();

        assertFalse(result.isEmpty());
    }

    @Test
    public void buscar() {
        String idValido = employee.getId();
        String idInvalido = UUID.randomUUID().toString();

        assertThrows(IOException.class, () -> employeeDAO.buscar(idInvalido));
        Employee employeSalvo = assertDoesNotThrow(() -> employeeDAO.buscar(idValido));

        assertEquals(employeSalvo, employee);
    }

    @Test
    public void salvar() throws IOException, ComprimentoInvalidoException {
        String id = employee.getId();
        Employee employeeSalvo = employeeDAO.buscar(id);
        Employee employeeNaoSalvo = new Employee("Jose", "Silveira", "j.silveira@email.com");

        assertEquals(employee, employeeSalvo);
        assertDoesNotThrow(() -> employeeDAO.salvar(employeeNaoSalvo));
    }

    @Test
    public void atualizarDados() throws IOException, ComprimentoInvalidoException, EmailInvalidoException {
        employee.setNome("bruno");
        employee.setEmail("b.silveira@email.com");

        Employee employeeSalvo = employeeDAO.buscar(employee.getId());

        assertNotEquals(employeeSalvo.getNome(), employee.getNome());
        assertNotEquals(employeeSalvo.getEmail(), employee.getEmail());

        Employee employeAtualizado = employeeDAO.salvar(employee);

        assertEquals(employeAtualizado, employee);
    }

    @Test
    public void apagar() {
        boolean apagou = assertDoesNotThrow(() -> employeeDAO.apagar(employee.getId()));

        assertTrue(apagou);
        assertThrows(IOException.class, () -> employeeDAO.buscar(employee.getId()));
    }
}