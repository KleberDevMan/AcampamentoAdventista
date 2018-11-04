
package com.example.kleber.acampamentoadventista.modelos.musica;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Musica {

    @SerializedName("artista")
    @Expose
    private String artista;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("letra")
    @Expose
    private String letra;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Musica() {
    }

    public Musica(String artista, String titulo, String letra, Integer id) {
        this.artista = artista;
        this.titulo = titulo;
        this.letra = letra;
        this.id = id;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
