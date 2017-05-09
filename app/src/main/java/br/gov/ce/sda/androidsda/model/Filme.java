package br.gov.ce.sda.androidsda.model;

import java.util.Date;
import java.util.List;

/**
 * POJO representing the Movie entity.
 * POJO representando a entidade Filmes.
 */

public class Filme {

    private Long id;
    private String titulo;
    private String categoria;
    private String diretor;
    private List<String> atores;
    private String sinopse;
    private Date dataLancamento;
    private Integer nota;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public List<String> getAtores() {
        return atores;
    }

    public void setAtores(List<String> atores) {
        this.atores = atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

}
