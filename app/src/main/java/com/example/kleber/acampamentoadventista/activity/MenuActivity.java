package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.kleber.acampamentoadventista.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void btnMusicas(View botao) {
        Intent intencao = new Intent(this, ListaMusicasActivity.class);
        this.startActivity(intencao);
    }
    public void btnCompartilhar(View botao) {
        Intent intencao = new Intent(this, CompartilharActivity.class);
        this.startActivity(intencao);
    }
    public void btnRoteiro(View botao) {
        Intent intencao = new Intent(this, RoteiroActivity.class);
        this.startActivity(intencao);
    }
    public void btnNews(View botao) {
        Intent intencao = new Intent(this, NewsActivity.class);
        this.startActivity(intencao);
    }
    public void btnContatos(View botao) {
        Intent intencao = new Intent(this, ContatosActivity.class);
        this.startActivity(intencao);
    }
    public void btnMeditacao(View botao) {
        Intent intencao = new Intent(this, MeditacaoActivity.class);
        this.startActivity(intencao);
    }
    //INFLANDO ITENS_DE_ MENU NA ACTION_BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // add o menu tres pontinhos
        inflater.inflate(R.menu.menu_tres_pontinhos, menu);

        return true;
    }
}
