package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }

    @Override
    public List<Employee> listar() {
        return dao.listar();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {
        try {
            Optional<Employee> employee = dao.buscar(id);
            if (employee.isPresent()) {
                return employee.get();
            }
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        } catch (NoSuchElementException ex) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
        if (employee == null)
            throw new IllegalArgumentException("employee inválido");
        try {
            return dao.salvar(employee);
        } catch (PersistenceException ex) {
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        if (employee == null)
            throw new IllegalArgumentException("employee inválido");
        if (employee.getId() == null)
            throw new IllegalArgumentException("employee inválido: não possui ID");
        buscar(employee.getId());
        try {
            salvar(employee);
        } catch (EmailInvalidoException e) {
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
        return employee;
    }

    @Override
    public void apagar(long id) throws BusinessException {
        buscar(id);
        try {
            dao.apagar(id);
        } catch (PersistenceException e) {
            throw new IllegalArgumentException("employee não pode ser excluido porque possui feedbacks vinculados");
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}