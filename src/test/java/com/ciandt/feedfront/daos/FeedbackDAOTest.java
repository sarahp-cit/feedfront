package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackDAOTest {
    private final LocalDate localDate = LocalDate.now();
    private final String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
    private Feedback feedback;
    private Employee autor;
    private Employee proprietario;
    private DAO<Feedback> feedbackDAO;

    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException {
        Files.walk(Paths.get("src/main/resources/data/feedback/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        feedbackDAO = new FeedbackDAO();
        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        feedbackDAO.salvar(feedback);
    }

    @Test
    public void listar() throws IOException {
        List<Feedback> result = feedbackDAO.listar();

        assertFalse(result.isEmpty());
    }

    @Test
    public void buscar() {
        String idValido = feedback.getId();
        String idInvalido = UUID.randomUUID().toString();

        assertThrows(IOException.class, () -> feedbackDAO.buscar(idInvalido));
        Feedback feedbackSalvo = assertDoesNotThrow(() -> feedbackDAO.buscar(idValido));

        assertEquals(feedbackSalvo.getId(), feedback.getId());
    }

    @Test
    public void salvar() throws IOException, ComprimentoInvalidoException {
        String id = feedback.getId();
        Feedback feedbackSalvo = feedbackDAO.buscar(id);
        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");
        Feedback feedbackNaoSalvo = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        assertEquals(feedback.getId(), feedbackSalvo.getId());
        assertDoesNotThrow(() -> feedbackDAO.salvar(feedbackNaoSalvo));
    }

    @Test
    public void atualizarDados() throws IOException, ComprimentoInvalidoException, EmailInvalidoException {
        feedback.setOqueMelhora("A cobertura de testes");
        feedback.setComoMelhora("Fazendo novos testes");
        Feedback feedbackSalvo = feedbackDAO.buscar(feedback.getId());

        assertNotEquals(feedbackSalvo.getComoMelhora(), feedback.getComoMelhora());
        assertNotEquals(feedbackSalvo.getOqueMelhora(), feedback.getOqueMelhora());

        Feedback feedbackAtualizado = feedbackDAO.salvar(feedback);

        assertEquals(feedbackAtualizado, feedback);
    }

    @Test
    public void apagar() {
        boolean apagou = assertDoesNotThrow(() -> feedbackDAO.apagar(feedback.getId()));
        assertTrue(apagou);
        assertThrows(IOException.class, () -> feedbackDAO.buscar(feedback.getId()));
    }
}
