package com.ciandt.feedfront.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeBuilder {
    public static List<Employee> arquivoToEmployees(List<String> employeesFile) {
        List<Employee> employees = new ArrayList<>();
        employees = employeesFile.stream().map(employee -> arquivoToEmployee(employee)).collect(Collectors.toList());
        return employees;
    }

    public static Employee arquivoToEmployee(String employeeFile) {
        String[] employee = employeeFile.split(";");
        return new Employee(employee[0], employee[1], employee[2], employee[3]);
    }

    public static String employeeToArquivo(Employee employee) {
        return employee.getId() + ";" + employee.getNome() + ";" +
                employee.getSobrenome() + ";" + employee.getEmail() + System.lineSeparator();
    }
}
