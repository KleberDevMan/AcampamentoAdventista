
package com.example.kleber.acampamentoadventista.modelos.vagalume;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("art")
    @Expose
    private Art art;
    @SerializedName("mus")
    @Expose
    private List<Mu> mus = null;
    @SerializedName("badwords")
    @Expose
    private Boolean badwords;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public List<Mu> getMus() {
        return mus;
    }

    public void setMus(List<Mu> mus) {
        this.mus = mus;
    }

    public Boolean getBadwords() {
        return badwords;
    }

    public void setBadwords(Boolean badwords) {
        this.badwords = badwords;
    }

}
