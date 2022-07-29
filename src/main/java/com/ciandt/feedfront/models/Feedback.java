package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "FEEDBACK")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public Feedback() {
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) throws ComprimentoInvalidoException {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao,
                    String oqueMelhora, String comoMelhora) throws ComprimentoInvalidoException {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
        setOqueMelhora(oqueMelhora);
        setComoMelhora(comoMelhora);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Employee getAutor() {
        return autor;
    }

    public void setAutor(Employee autor) {
        this.autor = autor;
    }

    public Employee getProprietario() {
        return proprietario;
    }

    public void setProprietario(Employee proprietario) {
        this.proprietario = proprietario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws ComprimentoInvalidoException {
        if (descricao.length() <= 2)
            throw new ComprimentoInvalidoException("Comprimento da descrição deve ser maior que 2 caracteres.");
        this.descricao = descricao;
    }

    public String getOqueMelhora() {
        return oQueMelhora;
    }

    public void setOqueMelhora(String oqueMelhora) {
        this.oQueMelhora = oqueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
    }

    @Override
    public String toString() {
        String nomeAutor;
        if (autor != null) {
            nomeAutor = autor.getNome();
        } else {
            nomeAutor = "Anônimo";
        }
        return "\nid=" + id +
                ", descricao='" + descricao + '\'' +
                ", oQueMelhora='" + oQueMelhora + '\'' +
                ", comoMelhora='" + comoMelhora + '\'' +
                ", data=" + data + '\'' +
                ", autor=" + nomeAutor + '\'' +
                ", proprietario=" + proprietario.getNome() + '\'';
    }
}
