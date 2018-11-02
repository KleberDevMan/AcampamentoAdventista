package com.example.kleber.acampamentoadventista.activity;

import android.content.Intent;
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

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorVideo;
import com.example.kleber.acampamentoadventista.api.YouTubeSevice;
import com.example.kleber.acampamentoadventista.helper.RetrofitConfig;
import com.example.kleber.acampamentoadventista.helper.YouTubeConfig;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.youtube.Item;
import com.example.kleber.acampamentoadventista.modelos.youtube.Resultado;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener{

    private RecyclerView recyclerVideos;

    private AdaptadorVideo adaptadorVideo;
    private MaterialSearchView searchView;

    //Modelos da API youTube
    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;

    //Retrofit
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        inicializaComponentes();
        configuraToolbar();

        //Configura retrofit
        retrofit = RetrofitConfig.getRetrofit();

        //Recupera Videos
        recuperarVideos();

        //DEFINE LISTENER SEARCHVIEW
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
    }



    private void configuraToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void inicializaComponentes() {
        recyclerVideos = findViewById(R.id.recyclerVideos);
        videos = new ArrayList<>();
        searchView = findViewById(R.id.materialSearchView);
    }

    private void recuperarVideos() {
        YouTubeSevice youTubeSevice = retrofit.create(YouTubeSevice.class);

        youTubeSevice.recuperarVideos(
                "snippet", "date",
                "20", YouTubeConfig.CHAVE_YOUTUBE_API,
                YouTubeConfig.CANAL_ID
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                //Log.d("resultado", "resultado: " + response.toString());
                Resultado resultado = response.body();
                videos = resultado.items;
                configurarRecyclerView();
//                Log.d("resultado", "resultado: " + resultado.items.get(0).id.videoId );
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });
    }

    public void configurarRecyclerView(){
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adaptadorVideo);

        //Configura evento de clique
        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Item video = videos.get(position);
                                String idVideo = video.id.videoId;
                                String titulo = video.snippet.title;
                                String descricao = video.snippet.description;

                                Intent i = new Intent(NewsActivity.this, PlayerActivity.class);
                                i.putExtra("idVideo", idVideo);
                                i.putExtra("title", titulo);
                                i.putExtra("descricao", descricao);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }


    //RECARREGA A LISTA COM TODAS OS VIDEOS
    private void recarregarVideos() {
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setAdapter(adaptadorVideo);
        adaptadorVideo.notifyDataSetChanged();
    }


    //LISTA AS VIDEOS POR PARAMETRO
    private void pesquisarVideos(String texto) {
        List<Item> videosBusca = new ArrayList<>();

        //busca na lista de musicas e salva em uma outra lista
        for (Item video : videos) {

//            String titulo = video.getTitulo().toLowerCase();
            String titulo = video.snippet.title.toLowerCase();

            if (titulo.contains(texto)) {
                videosBusca.add(video);
            }
        }

        //exibe na recycler uma lista de musicas filtrada
        adaptadorVideo = new AdaptadorVideo(videosBusca, this);
        recyclerVideos.setAdapter(adaptadorVideo);
        adaptadorVideo.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // se for a seta voltar
        if (item.getItemId() == android.R.id.home) {
            finish(); // fecha esta atividade e retorna à atividade de anterior (se houver)
        }
        return super.onOptionsItemSelected(item);
    }

    // ----------- EVENTOS OnQueryTextListener (quando digita um texto na busca) ------------
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null && !query.isEmpty()) {
            //converte o testo em letras minusculas
            pesquisarVideos(query.toLowerCase());
        }
        return true;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty()) {
            //converte o testo em letras minusculas
            pesquisarVideos(newText.toLowerCase());
        }
        return true;
    }







    // ----------- EVENTOS SearchViewListener (quando clica em um dos botoes da busca) ------------
    @Override
    public void onSearchViewShown() {

    }
    @Override
    public void onSearchViewClosed() {
        recarregarVideos();
    }
}
