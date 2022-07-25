package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;

import java.io.IOException;
import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {

    }

    @Override
    public List<Employee> listar() throws ArquivoException {
        try {
            return dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        try {
            return dao.buscar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException, IllegalArgumentException {
        if (employee == null)
            throw new IllegalArgumentException("employee inválido");
        List<Employee> employees = listar();
        for (Employee employ : employees) {
            if (!employ.getId().equals(employee.getId()) && employ.getEmail().equals(employee.getEmail())) {
                throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
            }
        }
        try {
            dao.salvar(employee);
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return employee;
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException, IllegalArgumentException {
        if (employee == null)
            throw new IllegalArgumentException("employee inválido");
        buscar(employee.getId());
        try {
            salvar(employee);
        } catch (EmailInvalidoException e) {
            throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return employee;
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        buscar(id);
        try {
            dao.apagar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}