package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.models.Employee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO implements DAO<Employee> {
    private final String repositorioPath = "src/main/resources/data/employee/";

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Employee> employees;
        Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

        List<String> files = paths
                .map(p -> p.getFileName().toString())
                .filter(p -> p.endsWith(".byte"))
                .map(p -> p.replace(".byte", ""))
                .collect(Collectors.toList());

        employees = files.stream().map(id -> {
            try {
                return buscar(id);
            } catch (IOException ex) {
                throw new EntidadeNaoSerializavelException();
            }
        }).collect(Collectors.toList());
        return employees;
    }

    @Override
    public Employee buscar(String id) throws IOException, EntidadeNaoSerializavelException {
        Employee employee;
        try {
            FileInputStream fileIn = new FileInputStream(repositorioPath + id + ".byte");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            employee = (Employee) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            throw new IOException("");
        }
        return employee;
    }

    @Override
    public Employee salvar(Employee employee) throws IOException, EntidadeNaoSerializavelException {
        FileOutputStream fileOut = new FileOutputStream(repositorioPath + employee.getArquivo());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(employee);
        out.close();
        fileOut.close();
        return employee;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException {
        Files.delete(Paths.get(repositorioPath + id + ".byte"));
        return true;
    }
}