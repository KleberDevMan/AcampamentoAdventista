package com.example.kleber.acampamentoadventista.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorVideo;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.youtubepojo.Item;
import com.example.kleber.acampamentoadventista.modelos.youtubepojo.YouTubeResult;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaVideosActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener,
        RecyclerItemClickListener.OnItemClickListener {

    private RecyclerView recyclerVideos;

    private AdaptadorVideo adaptadorVideo;
    private MaterialSearchView searchView;

    private List<Item> videos = new ArrayList<>();
    private List<Item> videosPesquisa = new ArrayList<>();

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



    //RECARREGA A LISTA COM TODAS OS VIDEOS
    private void recarregarVideos() {
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setAdapter(adaptadorVideo);
        adaptadorVideo.notifyDataSetChanged();
    }

    //BUSCA VIDEOS NA INTERNET
    private void recuperarVideos() {
        //THREAD
        BuscaVideos buscaVideos = new BuscaVideos(this);
        String url = "https://www.googleapis.com/youtube/v3/" +
                "playlistItems?part=snippet" +
                "&order=date" +
                "&maxResults=20" +
                "&key=AIzaSyDWg9KAPB1QFIWFMKIix7jyYt2DayNtaaQ" +
                "&playlistId=PLdKdSz4_lV0rcBygal6Pb4CYL2YQDpNGK";
        buscaVideos.execute(url);
    }

    //SETA ARRAY LIST NA RECYCLER
    public void configurarRecyclerView(){
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adaptadorVideo);
    }


    //LISTA OS VIDEOS POR PARAMETRO
    private void pesquisarVideos(String texto) {

        //LIMPA A LISTA COM VIDEOS QUE CONTEEM TEXTO DIGITADO
        this.videosPesquisa.clear();

        //BUSCA A LISTA NA LISTA DE VIDEOS E SALVA EM OUTRA LISTA
        for (Item video : videos) {

            String titulo = video.getSnippet().getTitle().toLowerCase();

            if (titulo.contains(texto)) {
                videosPesquisa.add(video);
            }
        }

        //RECONFIRA RECYCLER
        adaptadorVideo = new AdaptadorVideo(videosPesquisa, this);
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
        Item video;

        //ON CLICK NA LISTA COM TODOS OS VIDEOS
        if (videosPesquisa.size() == 0)
            video = videos.get(position);
        else
            //CLICK NA LISTA COM VIDEOS FILTRADOS
            video = videosPesquisa.get(position);

        Intent i = new Intent(ListaVideosActivity.this, PlayerActivity.class);
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













    class BuscaVideos extends AsyncTask<String, Void, YouTubeResult> {

        private ListaVideosActivity listaVideosActivity;
        private ProgressDialog vrProgress = null;

        public BuscaVideos(ListaVideosActivity listaVideosActivity) {
            this.listaVideosActivity = listaVideosActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            vrProgress = new ProgressDialog(listaVideosActivity);
            vrProgress.setCancelable(false);
            vrProgress.setCanceledOnTouchOutside(false);
            vrProgress.setMessage("Carregando...");
            vrProgress.setTitle("Aguarde!");
            vrProgress.show();

        }

        @Override
        protected YouTubeResult doInBackground(String... strings) {

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
                return null;
            }

            Gson gson = new Gson();
            //Converte String JSON para objeto Java
            return gson.fromJson(buffer.toString(), YouTubeResult.class);
        }

        @Override
        protected void onPostExecute(YouTubeResult youTubeResult) {
            super.onPostExecute(youTubeResult);
            vrProgress.dismiss();

            if (youTubeResult == null)
                Toast.makeText(listaVideosActivity, "Sem internet.", Toast.LENGTH_SHORT).show();
            else
                videos = youTubeResult.getItems();
                configurarRecyclerView();
        }
    }

}
