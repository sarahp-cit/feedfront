package com.ciandt.feedfront.arquivo;

import com.ciandt.feedfront.employee.Employee;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arquivo {
    public static void criarArquivoEmployee(String filePath, Employee employee) throws ArquivoException {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath + employee.getId() + ".byte");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(employee);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            throw new ArquivoException("");
        }
    }

    public static void deletarLinhaArquivo(String filePath) throws ArquivoException {
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException i) {
            throw new ArquivoException("");
        }
    }

    public static List<Employee> buscarTodosEmployeesArquivo(String filePath) throws ArquivoException {
        List<Employee> employees;
        try (Stream<Path> paths = Files.walk(Paths.get(filePath))) {
            employees = paths.filter(Files::isRegularFile).map(e -> {
                try {
                    return buscarEmployeePorIdArquivo(e.toString());
                } catch (EmployeeNaoEncontradoException ex) {
                    throw new RuntimeException(ex);
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return employees;
    }

    public static Employee buscarEmployeePorIdArquivo(String filePath) throws EmployeeNaoEncontradoException {
        Employee employee;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            employee = (Employee) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            throw new EmployeeNaoEncontradoException("Employee não encontrado");
        }
        return employee;
    }
}
