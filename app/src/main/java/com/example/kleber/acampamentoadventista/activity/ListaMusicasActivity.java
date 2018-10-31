package com.example.kleber.acampamentoadventista.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorMusicas;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.Musica;
import com.example.kleber.acampamentoadventista.modelos.vagalume.Example;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class ListaMusicasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Musica> musicas;
    private AdaptadorMusicas adaptadorMusicas;
    private MaterialSearchView searchView;

//    private String chaveApiVagalume = "c882899b279d9a9627e078f427933b9b";

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
//                                BuscaMuscia buscaMusica = new BuscaMuscia();
//                                String urlApi = "https://api.vagalume.com.br/search.php?art="+ m.getCantor()+ "&mus="+ m.getNome() +"&apikey="+ chaveApiVagalume;
//                                buscaMusica.execute(urlApi);
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

//        Gson gson = new Gson();
//
//        try {
//
//            BufferedReader br = new BufferedReader(new FileReader(""));
//
//            //Converte String JSON para objeto Java
//            Musica obj = gson.fromJson(br, Musica.class);
//
//            System.out.println(obj);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        musicas.add(new Musica("Mansão Sobre O Monte", "letra", "Hinário Adventista", carregaImagem(R.drawable.salvacaoeservico1)));
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

//    class BuscaMuscia extends AsyncTask<String, Void, Example>{
//
//        @Override
//        protected Example doInBackground(String... strings) {
//
//            String stringUrl = strings[0];
//            InputStream inputStream = null;
//            InputStreamReader inputStreamReader = null;
//            StringBuffer buffer = null;
//
//            try {
//
//                URL url = new URL(stringUrl);
//                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
//
//                // Recupera os dados em Bytes
//                inputStream = conexao.getInputStream();
//
//                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
//                inputStreamReader = new InputStreamReader( inputStream );
//
//                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
//                BufferedReader reader = new BufferedReader( inputStreamReader );
//                buffer = new StringBuffer();
//                String linha = "";
//
//                while((linha = reader.readLine()) != null){
//                    buffer.append( linha );
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Gson gson = new Gson();
//
//            //Converte String JSON para objeto Java
//            Example musica = gson.fromJson(buffer.toString(), Example.class);
//
//            return musica;
//        }
//
//        @Override
//        protected void onPostExecute(Example musica) {
//            super.onPostExecute(musica);
//
//            Toast.makeText(getApplicationContext(),musica.getMus().get(0).getText(),
//                                        Toast.LENGTH_SHORT)
//                                        .show();
//
//        }
//    }
}
