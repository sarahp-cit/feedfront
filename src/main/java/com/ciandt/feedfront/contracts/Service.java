package com.ciandt.feedfront.contracts;

import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;

public interface Service<T> {
    List<T> listar();

    T buscar(long id) throws BusinessException;

    T salvar(T t) throws BusinessException, IllegalArgumentException;

    T atualizar(T t) throws BusinessException, IllegalArgumentException;

    void apagar(long id) throws BusinessException;

    void setDAO(DAO<T> dao);
}
