package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackServiceTest {
    private final LocalDate localDate = LocalDate.now();
    private final String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
    private Feedback feedback;

    private Employee autor;

    private Employee proprietario;

    private Service<Feedback> service;

    private DAO<Feedback> feedbackDao;

    @BeforeEach
    public void initEach() throws IOException, BusinessException {

        service = new FeedbackService();

        feedbackDao = (DAO<Feedback>) Mockito.mock(DAO.class);

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        service.setDAO(feedbackDao);

        feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

    }

    @Test
    public void listar() throws IOException {
        List<Feedback> listaRetorno = new ArrayList<>();
        listaRetorno.add(feedback);
        when(feedbackDao.listar()).thenReturn(listaRetorno);
        service.setDAO(feedbackDao);
        List<Feedback> lista = assertDoesNotThrow(() -> service.listar());

        assertFalse(lista.isEmpty());
        assertTrue(lista.contains(feedback));
        assertEquals(1, lista.size());
    }

    @Test
    public void salvar() throws IOException, BusinessException, ComprimentoInvalidoException {
        Employee employeeNaoSalvo = new Employee("miguel", "vitor", "m.vitor@email.com");

        Feedback feedbackValido1 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);
        Feedback feedbackValido2 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        Feedback feedbackInvalido1 = new Feedback(localDate, null, null, "feedback sem autor e proprietario");
        Feedback feedbackInvalido2 = new Feedback(localDate, autor, employeeNaoSalvo, "feedback sem autor e proprietario");

        when(feedbackDao.salvar(feedbackValido1)).thenReturn(feedbackValido1);
        when(feedbackDao.salvar(feedbackValido2)).thenReturn(feedbackValido2);
        when(feedbackDao.buscar(feedbackValido2.getId())).thenReturn(feedbackValido2);
        when(feedbackDao.buscar(feedbackValido1.getId())).thenReturn(feedbackValido1);
        when(feedbackDao.buscar(feedbackInvalido2.getId())).thenThrow(new IOException());
        service.setDAO(feedbackDao);
        assertDoesNotThrow(() -> service.salvar(feedbackValido1));
        assertDoesNotThrow(() -> service.salvar(feedbackValido2));

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> service.salvar(feedbackInvalido1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> service.salvar(null));
        Exception exception3 = assertThrows(EntidadeNaoEncontradaException.class, () -> service.salvar(feedbackInvalido2));

        assertEquals("feedback inválido", exception1.getMessage());
        assertEquals("feedback inválido", exception2.getMessage());
        assertEquals("não foi possível encontrar o feedback", exception3.getMessage());
    }

    @Test
    public void buscar() throws IOException, BusinessException {
        Feedback feedbackNaoSalvo = new Feedback(localDate, autor, proprietario, "tt");
        when(feedbackDao.buscar(feedback.getId())).thenReturn(feedback);
        when(feedbackDao.buscar(feedbackNaoSalvo.getId())).thenThrow(new IOException());
        service.setDAO(feedbackDao);
        assertDoesNotThrow(() -> service.buscar(feedback.getId()));
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscar(feedbackNaoSalvo.getId()));

        assertEquals("não foi possível encontrar o feedback", exception.getMessage());
    }

    @Test
    public void apagar() throws IOException, ComprimentoInvalidoException {
        Feedback feedback2 = new Feedback(localDate, autor, null, "feedback sem autor e proprietario");
        String uuidValido = feedback.getId();
        String uuidInvalido = feedback2.getId();

        when(feedbackDao.apagar(uuidValido)).thenReturn(true);
        when(feedbackDao.apagar(uuidInvalido)).thenThrow(FileNotFoundException.class);

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.apagar(uuidInvalido));

        assertDoesNotThrow(() -> service.apagar(uuidValido));

    }
}
