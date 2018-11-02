package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //SQLite
        try {
            //CRIA BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //CRIA TABELA
            bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS roteiros ( titulo VARCHAR, conteudo VARCHAR )");

            Cursor cursor = bancoDeDados.rawQuery("SELECT titulo, conteudo FROM roteiros", null);

            int c = cursor.getCount();

            if (cursor.getCount() == 0 || cursor.getCount() < 5) {
                //APAGA ROTEIROS
                bancoDeDados.execSQL("DELETE FROM roteiros;");

                //INSERIR ROTEIROS
                bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Sexta', 'Conteudo vindo do SQLite - Sexta') ");
                bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Sabado', 'Conteudo vindo do SQLite - Sabado') ");
                bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Domingo', 'Conteudo vindo do SQLite - Domingo') ");
                bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Segunda', 'Conteudo vindo do SQLite - Segunda') ");
                bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Terca', 'Conteudo vindo do SQLite - Terca') ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
