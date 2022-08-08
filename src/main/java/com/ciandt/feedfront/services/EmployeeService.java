package com.ciandt.feedfront.services;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> listar();

    Employee buscar(Long id) throws BusinessException;

    Employee salvar(Employee e) throws BusinessException, IllegalArgumentException;

    Employee atualizar(Employee e) throws BusinessException, IllegalArgumentException;

    void apagar(Long id) throws BusinessException;
}