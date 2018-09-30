package com.example.kleber.acampamentoadventista.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorVideo;
import com.example.kleber.acampamentoadventista.modelos.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;

    private List<Video> videos;
    private AdaptadorVideo adaptadorVideo;
//    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //inicializa componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);
        videos = new ArrayList<>();
//        searchView = findViewById(R.id.searchView);

        //configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar( toolbar );
        // add o backButton a toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Configura RecyclerView
        recuperarVideos();
        adaptadorVideo = new AdaptadorVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adaptadorVideo);

        //Configura os métodos para o onSearchView
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//
//            }
//        });
    }

    private void recuperarVideos() {

        Video video1 = new Video();
        video1.setTitulo("Video 1 muito interessante");
        videos.add(video1);

        Video video2 = new Video();
        video2.setTitulo("Video 2 muito interessante");
        videos.add(video2);

        Video video3 = new Video();
        video3.setTitulo("Video 3 muito interessante");
        videos.add(video3);
    }

    //INFLANDO O LUPA NA BARRA NO ACTION_BAR DA PAGINA DE NOTICIAS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        //o caminho do menu
//        menuInflater.inflate(R.menu.menu_lupa, menu);
//
//        //recupero o item lupa
//        MenuItem item = menu.findItem(R.id.menu_lupa);
//        searchView.setMenuItem( item );
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
}
