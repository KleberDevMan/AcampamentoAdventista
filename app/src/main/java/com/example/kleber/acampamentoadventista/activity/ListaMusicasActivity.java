package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorMusicas;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.musicapojo.Musica;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ListaMusicasActivity extends AppCompatActivity
        implements RecyclerItemClickListener.OnItemClickListener,
        MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener {

    private RecyclerView recyclerView;

    private List<Musica> musicas;
    private List<Musica> musicasPesquisa;
    private AdaptadorMusicas adaptadorMusicas;
    private MaterialSearchView searchView;

    //DICIONARIO
    private Bundle dicionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_musicas);

        inicializaComponentes();
        configuraToolbar();

        //RECUPERA AS MUSICAS
        recuperaMusicas();

        //CONFIGURA RECYCLER
        adaptadorMusicas = new AdaptadorMusicas(this, musicas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorMusicas);

        //DEFINE LISTENER RECYCLER
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, this));

        //DEFINE LISTENER SEARCHVIEW
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
    }

    private void configuraToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }
    private void inicializaComponentes() {
        recyclerView = findViewById(R.id.lista_musicas);
        musicas = new ArrayList<>();
        musicasPesquisa = new ArrayList<>();
        searchView = findViewById(R.id.materialSearchView);
        dicionario = new Bundle();
    }

    //RECARREGA A LISTA COM TODAS AS MUSICAS
    private void recarregarMusicas() {
        musicasPesquisa = musicas;
        adaptadorMusicas = new AdaptadorMusicas(this, musicasPesquisa);
        recyclerView.setAdapter(adaptadorMusicas);
        adaptadorMusicas.notifyDataSetChanged();
    }

    //RECUPERA AS MUSICAS DO BANCO
    private void recuperaMusicas() {

        //SQLite
        try {
            //ABRIR BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //RECUPERAR
            Cursor cursor = bancoDeDados.rawQuery("SELECT titulo, artista, letra, id, url_imagem FROM musicas ORDER BY titulo ASC", null);

            //INDICES DA TABELA
            int indiceTitulo = cursor.getColumnIndex("titulo");
            int indiceArtista = cursor.getColumnIndex("artista");
            int indiceLetra = cursor.getColumnIndex("letra");
            int indiceId = cursor.getColumnIndex("id");
            int indiceUrlImagem = cursor.getColumnIndex("url_imagem");

            //PERCORE TABELA
            int i = 0;
            cursor.moveToFirst();
            while (cursor != null) {

                String artist = cursor.getString(indiceArtista);
                String title = cursor.getString(indiceTitulo);
                String lyric = cursor.getString(indiceLetra);
                Integer id = cursor.getInt(indiceLetra);
                String urlImage = cursor.getString(indiceUrlImagem);

                musicas.add(new Musica(id, title, lyric, artist, urlImage));

                cursor.moveToNext();
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //FILTA MUSICAS
    private void pesquisarMusicas(String texto) {

        //LIMPA A LISTA COM MUSICAS QUE CONTEEM TEXTO DIGITADO
        this.musicasPesquisa.clear();

        //PREENCHE LISTA COM MUSICAS QUE CONTEEM TEXTO DIGITADO
        for (Musica musica : this.musicas) {

            String nome = musica.getTitle().toLowerCase();
            String cantor = musica.getArtist().toLowerCase();
            String letra = musica.getLyric().toLowerCase();

            if (nome.contains(texto) || cantor.contains(texto) || letra.contains(texto)) {
                this.musicasPesquisa.add(musica);
            }
        }

        //EXIBE LISTA COM MUSICAS QUE CONTEEM TEXTO DIGITADO
        adaptadorMusicas = new AdaptadorMusicas(this, this.musicasPesquisa);
        recyclerView.setAdapter(adaptadorMusicas);
        adaptadorMusicas.notifyDataSetChanged();
    }

    //INFLANDO ITENS DE MENU NA ACTION_BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // add o btnVoltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        MenuInflater inflater = getMenuInflater();
        // add o btnPesquisa
        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem item = menu.findItem(R.id.menu_pesquisa);
        searchView.setMenuItem(item);

        return true;
    }

















    // ----------- EVENTOS RecyclerView ------------
    @Override
    public void onItemClick(View view, int position) {
        Musica m;

        //ON CLICK NA LISTA COM TODAS AS MUSICAS
        if (musicasPesquisa.size() == 0)
            m = musicas.get(position);
        else
            //CLICK NA LISTA COM MUSICAS FILTRADAS
            m = musicasPesquisa.get(position);

        //EXIBE A MUSICA
        Intent intent = new Intent(this, MusicaActivity.class);
        dicionario.putSerializable("musica", m);
        intent.putExtras(dicionario);
        startActivity(intent);
    }
    @Override
    public void onLongItemClick(View view, int position) {
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }














    // ----------- EVENTOS OnQueryTextListener (quando digita um texto na busca) ------------
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null && !query.isEmpty()) {
            //converte o testo em letras minusculas
            pesquisarMusicas(query.toLowerCase());
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty()) {
            //converte o testo em letras minusculas
            pesquisarMusicas(newText.toLowerCase());
        }
        return true;
    }


    // ----------- EVENTOS SearchViewListener (quando clica em um dos botoes da busca) ------------
    @Override
    public void onSearchViewShown() {
    }
    @Override
    public void onSearchViewClosed() {
        recarregarMusicas();
    }

}
