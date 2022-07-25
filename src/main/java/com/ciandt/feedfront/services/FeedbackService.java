package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;

public class FeedbackService implements Service<Feedback> {

    private DAO<Feedback> dao;

    public FeedbackService() {

    }

    @Override
    public List<Feedback> listar() throws ArquivoException {
        try {
            return dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        try {
            return dao.buscar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
        }
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        if (feedback == null || feedback.getProprietario() == null || feedback.getAutor() == null)
            throw new IllegalArgumentException("feedback inválido");
        buscar(feedback.getId());
        try {
            dao.salvar(feedback);
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return feedback;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        if (feedback == null)
            throw new IllegalArgumentException("feedback inválido");
        buscar(feedback.getId());
        try {
            salvar(feedback);
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return feedback;
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        buscar(id);
        try {
            dao.apagar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
        }
    }

    @Override
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }
}
