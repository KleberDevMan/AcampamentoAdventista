package com.example.kleber.acampamentoadventista.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        searchView = findViewById(R.id.materialSearchView);

        //configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

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
                                        "musica " + m.getNome() + " precionada",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Musica m = musicas.get(position);
                                Toast.makeText(getApplicationContext(),
                                        "musica " + m.getNome() + " precionada com CLICK LONGO",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        })
        );


        //ouve eventos do onSearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            //chamado quando o user confirma o que digitor
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.isEmpty()) {
                    //converte o testo em letras minusculas
                    pesquisarMusicas(query.toLowerCase());
                }
                return true;
            }
            //chamado em tempo de execucao
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    //converte o testo em letras minusculas
                    pesquisarMusicas(newText.toLowerCase());
                }
                return true;
            }
        });
        //ouve comportamento da SeachView
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            //assim que o SeachView é EXIBIDO
            @Override
            public void onSearchViewShown() {

            }
            //assim que o SeachView é FECHADO
            @Override
            public void onSearchViewClosed() {
                recarregarMusicas();
            }
        });
    }



    //RECARREGA A LISTA COM TODAS AS MUSICAS
    private void recarregarMusicas() {
        adaptadorMusicas = new AdaptadorMusicas(this, musicas);
        recyclerView.setAdapter(adaptadorMusicas);
        adaptadorMusicas.notifyDataSetChanged();
    }

    //LISTA AS MUSICAS POR PARAMETRO
    private void pesquisarMusicas(String texto) {
        List<Musica> musciasBusca = new ArrayList<>();

        //busca na lista de musicas e salva em uma outra lista
        for (Musica musica : musicas) {

            String nome = musica.getNome().toLowerCase();
            String cantor = musica.getCantor().toLowerCase();

            if (nome.contains(texto) || cantor.contains(texto)) {
                musciasBusca.add(musica);
            }
        }

        //exibe na recycler uma lista de musicas filtrada
        adaptadorMusicas = new AdaptadorMusicas(this, musciasBusca);
        recyclerView.setAdapter(adaptadorMusicas);
        adaptadorMusicas.notifyDataSetChanged();
    }

    //RECUPERA AS MUSICAS VINDAS DO BANCO
    private void recuperaMusicas() {
        musicas.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        musicas.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        musicas.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        musicas.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        musicas.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        musicas.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
    }

    //CONVERTE IMAGENS SALVAS NOS RECURSOS EM BITMAP
    private Bitmap carregaImagem(int id) {
        return BitmapFactory
                .decodeResource(getResources(), id);
    }

    //INFLANDO ITENS_DE_ MENU NA ACTION_BAR
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

    //OUVIR O BTN VOLTAR
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // se for a seta voltar
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
