
package com.example.kleber.acampamentoadventista.modelos.minhas_musicas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Musica {

    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("letra")
    @Expose
    private String letra;

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

}
