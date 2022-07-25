package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.models.Feedback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedbackDAO implements DAO<Feedback> {

    private final String repositorioPath = "src/main/resources/data/feedback/";

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Feedback> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Feedback> feedbacks;
        Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

        List<String> files = paths
                .map(p -> p.getFileName().toString())
                .filter(p -> p.endsWith(".byte"))
                .map(p -> p.replace(".byte", ""))
                .collect(Collectors.toList());

        feedbacks = files.stream().map(id -> {
            try {
                return buscar(id);
            } catch (IOException ex) {
                throw new EntidadeNaoSerializavelException();
            }
        }).collect(Collectors.toList());
        return feedbacks;
    }

    @Override
    public Feedback buscar(String id) throws IOException, EntidadeNaoSerializavelException {
        Feedback feedback;
        try {
            FileInputStream fileIn = new FileInputStream(repositorioPath + id + ".byte");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            feedback = (Feedback) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            throw new IOException("");
        }
        return feedback;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws IOException, EntidadeNaoSerializavelException {
        FileOutputStream fileOut = new FileOutputStream(repositorioPath + feedback.getArquivo());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(feedback);
        out.close();
        fileOut.close();
        return feedback;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException {
        Files.delete(Paths.get(repositorioPath + id + ".byte"));
        return true;
    }
}
