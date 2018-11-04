
package com.example.kleber.acampamentoadventista.modelos.musica;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Musicas {

    @SerializedName("versao")
    @Expose
    private String versao;
    @SerializedName("musicas")
    @Expose
    private List<Musica> musicas = null;

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }
}
