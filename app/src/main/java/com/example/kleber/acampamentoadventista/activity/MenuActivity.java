package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
//import com.example.kleber.acampamentoadventista.modelos.musica.Musicas;
import com.example.kleber.acampamentoadventista.modelos.musicapojo.Musica;
import com.example.kleber.acampamentoadventista.modelos.musicapojo.Musicas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements ValueEventListener {

    //FIREBASE REFERENCE
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    //MUSICAS
    private List<Musica> musicas = new ArrayList<>();

    //WEB_SERVICE
    private String urlMusicas = "https://fierce-inlet-45074.herokuapp.com/musics.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //FIREBASE
//        final DatabaseReference roteirosDb = referencia.child("roteiros");
        referencia.addValueEventListener(this);

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


            //-------------------- BAIXA A LETRA DAS MUSICAS -----------------------
            BuscaMuscia buscaMusica = new BuscaMuscia(this, bancoDeDados, dataSnapshot);
            buscaMusica.execute(urlMusicas);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }


    private void insereNoDBLocal(SQLiteDatabase databaseRemote, DataSnapshot dataSnapshot, String dia) {
        String titulo = "";
        String conteudo = "";

        titulo = (String) dataSnapshot.child("roteiros").child(dia).child("titulo").getValue();
        conteudo = (String) dataSnapshot.child("roteiros").child(dia).child("conteudo").getValue();
        databaseRemote.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('" + titulo + "', '" + conteudo + "') ");
    }

    class BuscaMuscia extends AsyncTask<String, Void, List<Musica>> {

        //        ProgressDialog vrProgress = null;
        private AppCompatActivity activity = null;
        private SQLiteDatabase bancoDeDados;
        private DataSnapshot dataSnapshot;

        public BuscaMuscia(AppCompatActivity activity) {
            this.activity = activity;
        }

        public BuscaMuscia(AppCompatActivity activity, SQLiteDatabase bancoDeDados, DataSnapshot dataSnapshot) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        protected List<Musica> doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(activity, "Não foi possivel sincronizar.", Toast.LENGTH_SHORT);
            }

            Gson gson = new Gson();

            List<Musica> musicas = null;

            Type collectionType = new TypeToken<List<Musica>>() {}.getType();

            try {
//                musicas.setMusicas((List<Musica>) gson.fromJson(buffer.toString(), Musica.class));
                musicas = gson.fromJson(buffer.toString(), collectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (musicas == null)
                return null;
            else
                return musicas;
        }

        @Override
        protected void onPostExecute(List<Musica> musicas) {
            super.onPostExecute(musicas);

            if (musicas == null) {
                Toast.makeText(activity, "Não foi possível sincronizar.", Toast.LENGTH_LONG).show();
            } else {

                bancoDeDados.execSQL("DROP TABLE IF EXISTS musicas");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS musicas ( titulo VARCHAR, artista VARCHAR, letra VARCHAR, id INTEGER, url_imagem VARCHAR)");

                //APAGA MUSICAS
                bancoDeDados.execSQL("DELETE FROM musicas;");

                for (Musica m :
                        musicas) {
                    //INSERE MUSICA
                    insereMusicaDBLocal(bancoDeDados, dataSnapshot, m);
                }


                Toast.makeText(activity, "SINCRONIZADO.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void insereMusicaDBLocal(SQLiteDatabase bancoDeDados, DataSnapshot dataSnapshot, Musica m) {

        bancoDeDados.execSQL("INSERT INTO musicas(titulo, artista, letra, id, url_imagem) VALUES('" + m.getTitle() + "', '" + m.getArtist() + "', '" + m.getLyric() + "', '" + m.getId() + "','" + m.getUrlImage() + "') ");

    }

}
