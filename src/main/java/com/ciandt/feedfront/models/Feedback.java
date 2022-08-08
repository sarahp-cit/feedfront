package com.ciandt.feedfront.models;

import com.ciandt.feedfront.exceptions.ComprimentoInvalidoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "FEEDBACK")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, message = "A descrição deve ter mais de 2 caracteres")
    @Column(name = "descricao", nullable = false)
    private String descricao;
    @Column(name = "oQueMelhora")
    private String oQueMelhora;
    @Column(name = "comoMelhora")
    private String comoMelhora;
    @Column(name = "data", nullable = false)
    private LocalDate data;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Employee autor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Employee proprietario;

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) throws ComprimentoInvalidoException {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao,
                    String oQueMelhora, String comoMelhora) throws ComprimentoInvalidoException {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
        setOQueMelhora(oQueMelhora);
        setComoMelhora(comoMelhora);
    }

}
