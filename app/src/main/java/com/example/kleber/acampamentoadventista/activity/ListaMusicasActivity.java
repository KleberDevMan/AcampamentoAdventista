package com.example.kleber.acampamentoadventista.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorMusicas;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.Musica;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ListaMusicasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Musica> musicas;
    private AdaptadorMusicas adaptadorMusicas;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_musicas);

        //inicializa componentes
        recyclerView = findViewById(R.id.lista_musicas);
        musicas = new ArrayList<>();
        searchView = findViewById(R.id.searchView);

        //configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar( toolbar );

        //Configura RecyclerView
        recuperaMusicas();
        adaptadorMusicas = new AdaptadorMusicas(this, musicas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorMusicas);

        //EVENTO DE CLICK NA RECYCLE
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Musica m = musicas.get(position);
                                Toast.makeText(getApplicationContext(),
                                        "musica "+m.getNome()+" precionada",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Musica m = musicas.get(position);
                                Toast.makeText(getApplicationContext(),
                                        "musica "+m.getNome()+" precionada com CLICK LONGO",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        })
        );


        //Configura os métodos para o onSearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

    }

    private void recuperaMusicas() {
        musicas.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        musicas.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        musicas.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        musicas.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        musicas.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        musicas.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
    }

    private Bitmap carregaImagem(int id) {
        return BitmapFactory
                .decodeResource(getResources(), id);
    }

    //INFLANDO O LUPA NA BARRA NO ACTION_BAR DA PAGINA DE NOTICIAS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        //o caminho do menu
        menuInflater.inflate(R.menu.menu_lupa, menu);

        //recupero o item lupa
        MenuItem item = menu.findItem(R.id.menu_lupa);
        searchView.setMenuItem( item );
        return true;
    }
}
