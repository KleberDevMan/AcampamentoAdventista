package com.example.kleber.acampamentoadventista.modelos;

import android.graphics.Bitmap;

public class Musica {

    private String nome;
    private String letra;
    private String cantor;
    private Bitmap album;

    public Musica() {
    }

    public Musica(String nome, String letra, String cantor, Bitmap album) {
        this.nome = nome;
        this.letra = letra;
        this.cantor = cantor;
        this.album = album;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getCantor() {
        return cantor;
    }

    public void setCantor(String cantor) {
        this.cantor = cantor;
    }

    public Bitmap getAlbum() {
        return album;
    }

    public void setAlbum(Bitmap album) {
        this.album = album;
    }
}
