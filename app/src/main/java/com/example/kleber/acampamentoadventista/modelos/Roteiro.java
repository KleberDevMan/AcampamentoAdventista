package com.example.kleber.acampamentoadventista.modelos;

import java.io.Serializable;

public class Roteiro implements Serializable {

    private String titulo;
    private String conteudo;

    public Roteiro() {
    }

    public Roteiro(String titulo, String conteudo) {
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        return "RoteiroFragment{" +
                "titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
}
