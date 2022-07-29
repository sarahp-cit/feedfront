package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class FeedbackService implements Service<Feedback> {

    private DAO<Feedback> dao;
    private Service<Employee> employeeService;

    public FeedbackService() {
        this.dao = new FeedbackDAO();
        this.employeeService = new EmployeeService();
    }

    @Override
    public List<Feedback> listar() {
        return dao.listar();
    }

    @Override
    public Feedback buscar(long id) throws BusinessException {
        try {
            Optional<Feedback> feedback = dao.buscar(id);
            if (feedback.isPresent()) {
                return feedback.get();
            }
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
        } catch (NoSuchElementException ex) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
        }
    }

    @Override
    public Feedback salvar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        if (feedback == null)
            throw new IllegalArgumentException("feedback inválido");
        if (feedback.getProprietario() == null)
            throw new IllegalArgumentException("employee inválido");
        employeeService.buscar(feedback.getProprietario().getId());
        if (feedback.getAutor() != null)
            employeeService.buscar(feedback.getAutor().getId());
        dao.salvar(feedback);
        return feedback;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        if (feedback == null)
            throw new IllegalArgumentException("feedback inválido");
        buscar(feedback.getId());
        salvar(feedback);
        return feedback;
    }

    @Override
    public void apagar(long id) throws BusinessException {
        buscar(id);
        dao.apagar(id);
    }

    @Override
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }

    public void setEmployeeService(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }
}
