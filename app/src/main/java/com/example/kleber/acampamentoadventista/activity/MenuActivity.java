package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public void btnRoteiro(View botao) {
        Intent intencao = new Intent(this, RoteiroActivity.class);
        this.startActivity(intencao);
    }
    public void btnNews(View botao) {
        Intent intencao = new Intent(this, NewsActivity.class);
        this.startActivity(intencao);
    }


}
