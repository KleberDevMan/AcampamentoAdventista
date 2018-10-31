
package com.example.kleber.acampamentoadventista.modelos.minhas_musicas;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Musicas {

    @SerializedName("musicas")
    @Expose
    private List<Musica> musicas = null;

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

}
