package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @ApiOperation(value = "Retorna todos os employees")
    @GetMapping
    public ResponseEntity<List<Employee>> listar() {
        return new ResponseEntity<>(employeeService.listar(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca um employee por Id")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> buscar(@PathVariable long id) throws BusinessException {
        return new ResponseEntity<>(employeeService.buscar(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Salva um employee")
    @PostMapping
    public ResponseEntity<Employee> salvar(Employee employee) throws BusinessException {
        return new ResponseEntity<>(employeeService.salvar(employee), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Exclui um employee por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable long id) throws BusinessException {
        employeeService.apagar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Atualiza um employee")
    @PutMapping
    public ResponseEntity<Employee> atualizar(Employee employee) throws BusinessException {
        return new ResponseEntity<>(employeeService.salvar(employee), HttpStatus.OK);
    }
}