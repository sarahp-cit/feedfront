package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.controller.EmployeeController;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {
    private Employee employee;
    private EmployeeController employeeController;
    private Service<Employee> employeeService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void initEach() throws ArquivoException, BusinessException, ComprimentoInvalidoException {
        employeeController = new EmployeeController();
        employeeService = (Service<Employee>) Mockito.mock(Service.class);
        employee = new Employee("João", "Silveira", "j.silveira@email.com");

        employeeController.setService(employeeService);

        employeeController.salvar(employee);
    }

    @Test
    public void listar() {
        Collection<Employee> employees = assertDoesNotThrow(employeeController::listar);

        assertNotNull(employees);
    }

    @Test
    public void buscar() throws IOException, BusinessException {
        String uuid = employee.getId();
        when(employeeService.buscar(uuid)).thenReturn(employee);

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeController.buscar(uuid));

        assertEquals(employee, employeeSalvo);
    }

    @Test
    public void salvar() throws IOException, BusinessException {
        when(employeeService.salvar(employee)).thenReturn(employee);

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeController.salvar(employee));

        assertEquals(employee, employeeSalvo);
    }

    @Test
    public void atualizar() throws IOException, BusinessException, EmailInvalidoException {
        String uuid = employee.getId();
        employee.setEmail("joao.silveira@email.com");

        when(employeeService.buscar(uuid)).thenReturn(employee);
        when(employeeService.atualizar(employee)).thenReturn(employee);

        Employee employeeAtualizado = assertDoesNotThrow(() -> employeeController.atualizar(employee));

        assertEquals(employee, employeeAtualizado);
    }

    @Test
    public void apagar() throws IOException, BusinessException {
        String uuid = employee.getId();
        when(employeeService.buscar(uuid)).thenReturn(employee);

        assertDoesNotThrow(() -> employeeController.apagar(uuid));
    }
}
