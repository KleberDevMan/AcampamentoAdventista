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
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorVideo;
import com.example.kleber.acampamentoadventista.api.YouTubeSevice;
import com.example.kleber.acampamentoadventista.helper.RetrofitConfig;
import com.example.kleber.acampamentoadventista.helper.YouTubeConfig;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.youtube.Item;
import com.example.kleber.acampamentoadventista.modelos.youtube.ResultYouTubeRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener,
        RecyclerItemClickListener.OnItemClickListener {

    private RecyclerView recyclerVideos;

    private AdaptadorVideo adaptadorVideo;
    private MaterialSearchView searchView;

    //Modelos da API youTube
    private List<Item> videos = new ArrayList<>();

    //Retrofit
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        inicializaComponentes();
        configuraToolbar();

        //DEFINE LISTENER RECYCLER
        recyclerVideos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerVideos, this));

        //DEFINE LISTENER SEARCHVIEW
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);

        //CONFIGURA RetroFit
        retrofit = RetrofitConfig.getRetrofit();

        //BUSCA VIDEOS NA INTERNET
        recuperarVideos();
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

    //BUSCA VIDEOS NA INTERNET
    private void recuperarVideos() {
        YouTubeSevice youTubeSevice = retrofit.create(YouTubeSevice.class);

        String s = "abre";

        //FAZ REQUISICAO HTTP
        youTubeSevice.recuperarVideos(
                "snippet", "date",
                "10", YouTubeConfig.CHAVE_YOUTUBE_API,
                YouTubeConfig.PLAYLIST

        ).enqueue(new Callback<ResultYouTubeRequest>() {

            //LE A RESPOSTA E COLOCA NA RECYCLER
            @Override
            public void onResponse(Call<ResultYouTubeRequest> call, Response<ResultYouTubeRequest> response) {
                ResultYouTubeRequest resultado = response.body();
                videos = resultado.getItems();
                configurarRecyclerView();

                String s = "fecha";
            }


            @Override
            public void onFailure(Call<ResultYouTubeRequest> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Sem internet. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //SETA ARRAY LIST NA RECYCLER
    public void configurarRecyclerView(){
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adaptadorVideo);
    }

    //RECARREGA A LISTA COM TODAS OS VIDEOS
    private void recarregarVideos() {
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setAdapter(adaptadorVideo);
        adaptadorVideo.notifyDataSetChanged();
    }

    //LISTA OS VIDEOS POR PARAMETRO
    private void pesquisarVideos(String texto) {
        List<Item> videosBusca = new ArrayList<>();

        //BUSCA A LISTA NA LISTA DE VIDEOS E SALVA EM OUTRA LISTA
        for (Item video : videos) {

            String titulo = video.getSnippet().getTitle().toLowerCase();

            if (titulo.contains(texto)) {
                videosBusca.add(video);
            }
        }

        //RECONFIRA RECYCLER
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

    //EVENTO VOLTAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // SE FOR BTN VOLTAR
        if (item.getItemId() == android.R.id.home) {
            finish();
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


    // ----------- EVENTOS RecyclerView ------------
    @Override
    public void onItemClick(View view, int position) {
        Item video = videos.get(position);

        Intent i = new Intent(NewsActivity.this, PlayerActivity.class);
        i.putExtra("idVideo", video.getSnippet().getResourceId().getVideoId());
        i.putExtra("title", video.getSnippet().getTitle());
        i.putExtra("descricao", video.getSnippet().getDescription());
        startActivity(i);
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
