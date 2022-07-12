package com.ciandt.feedfront.arquivo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arquivo {
    public static void inserirLinhaArquivo(String filePath, String novaLinha) {
        try {
            Files.write(Paths.get(filePath), novaLinha.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void alterarLinhaArquivo(String filePath, String id, String alterarLinha) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            String employee = buscarPorIdArquivo(filePath, id);
            lines.set(lines.indexOf(employee), alterarLinha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deletarLinhaArquivo(String filePath, String id) {
        List<String> employees;
        try {
            Path path = Paths.get(filePath);
            Stream<String> stream = Files.lines(path);
            employees = stream
                    .filter(line -> !line.startsWith(id))
                    .map(String::toString)
                    .collect(Collectors.toList());
            Files.delete(path);
            Files.write(path, employees, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> lerArquivo(String filePath) {
        List<String> employees = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            employees = stream.map(String::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static String buscarPorIdArquivo(String filePath, String id) {
        String employee = "";
        try {
            Stream<String> stream = Files.lines(Paths.get(filePath));
            employee = stream
                    .filter(line -> line.startsWith(id))
                    .map(String::toString)
                    .collect(Collectors.toList()).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
