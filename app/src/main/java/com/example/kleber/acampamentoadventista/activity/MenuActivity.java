package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;
import com.google.android.youtube.player.internal.d;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity implements ValueEventListener {

//    //FIREBASE REFERENCE
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //FIREBASE
        final DatabaseReference roteirosDb = referencia.child("roteiros");
        roteirosDb.addValueEventListener(this);

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

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        //SQLite
        try {
            //CRIA/ABRE BANCO LOCAL
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //CRIA TABELA
            bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS roteiros ( titulo VARCHAR, conteudo VARCHAR )");

            //APAGA ROTEIROS
            bancoDeDados.execSQL("DELETE FROM roteiros;");

            //INSERE ROTEIROS
            insereNoDBLocal(bancoDeDados, dataSnapshot, Roteiros.SEXTA.name().toLowerCase());
            insereNoDBLocal(bancoDeDados, dataSnapshot, Roteiros.SABADO.name().toLowerCase());
            insereNoDBLocal(bancoDeDados, dataSnapshot, Roteiros.DOMINGO.name().toLowerCase());
            insereNoDBLocal(bancoDeDados, dataSnapshot, Roteiros.SEGUNDA.name().toLowerCase());
            insereNoDBLocal(bancoDeDados, dataSnapshot, Roteiros.TERCA.name().toLowerCase());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insereNoDBLocal(SQLiteDatabase databaseRemote, DataSnapshot dataSnapshot, String dia) {
        String titulo = "";
        String conteudo = "";

        titulo = (String) dataSnapshot.child(dia).child("titulo").getValue();
        conteudo = (String) dataSnapshot.child(dia).child("conteudo").getValue();
        databaseRemote.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('"+titulo+"', '"+conteudo+"') ");
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
