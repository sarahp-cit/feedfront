package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.controller.FeedbackController;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackControllerTest {
    private Feedback feedback;
    private Employee autor;
    private Employee proprietario;
    private FeedbackController controller;

    private Service<Feedback> service;

    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException {
        controller = new FeedbackController();
        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");
        service = Mockito.mock(Service.class);
        controller.setService(service);
        feedback = new Feedback(LocalDate.now(), autor, proprietario, "Agradeco muito pelo apoio feito pelo colega!");//construtor 1

    }

    @Test
    public void listar() throws ArquivoException {
        List<Feedback> lista = new ArrayList<>();
        lista.add(feedback);
        when(service.listar()).thenReturn(lista);
        Collection<Feedback> listaFeedback = controller.listar();

        assertNotNull(listaFeedback);
    }

    @Test
    public void salvar() throws BusinessException, ArquivoException {
        when(service.salvar(feedback)).thenReturn(feedback);
        assertDoesNotThrow(() -> controller.salvar(feedback));
    }

    @Test
    public void buscar() throws BusinessException, ArquivoException {

        String uuid = feedback.getId();

        when(service.buscar(uuid)).thenReturn(feedback);

        Feedback feedbackSalvo = assertDoesNotThrow(() -> controller.buscar(uuid));

        assertEquals(feedback, feedbackSalvo);

    }

    @Test
    public void atualizar() throws IOException, BusinessException, EmailInvalidoException {
        String uuid = feedback.getId();
        feedback.setOqueMelhora("Cobertura de código");

        when(service.buscar(uuid)).thenReturn(feedback);
        when(service.atualizar(feedback)).thenReturn(feedback);

        Feedback feedbackAtualizado = assertDoesNotThrow(() -> controller.atualizar(feedback));

        assertEquals(feedback, feedbackAtualizado);
    }

    @Test
    public void apagar() throws IOException, BusinessException {
        String uuid = feedback.getId();
        when(service.buscar(uuid)).thenReturn(feedback);

        assertDoesNotThrow(() -> controller.apagar(uuid));
    }
}
