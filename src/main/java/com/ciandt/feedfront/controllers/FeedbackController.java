package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.services.FeedbackService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @ApiOperation(value = "Retorna todos os feedbacks")
    @GetMapping
    public ResponseEntity<List<Feedback>> listar() {
        return new ResponseEntity<>(feedbackService.listar(), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna feedback por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> buscar(@PathVariable long id) throws BusinessException {
        return new ResponseEntity<>(feedbackService.buscar(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Salva um feedback")
    @PostMapping
    public ResponseEntity<Feedback> salvar(@RequestBody Feedback feedback) throws BusinessException {
        return new ResponseEntity<>(feedbackService.salvar(feedback), HttpStatus.CREATED);
    }
}