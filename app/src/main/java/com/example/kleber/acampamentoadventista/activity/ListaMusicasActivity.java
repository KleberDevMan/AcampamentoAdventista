package com.example.kleber.acampamentoadventista.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.kleber.acampamentoadventista.modelos.musica.Musica;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ListaMusicasActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener,
        MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener {

    private RecyclerView recyclerView;

    private List<Musica> musicas;
    private AdaptadorMusicas adaptadorMusicas;
    private MaterialSearchView searchView;

//    private String chaveApiVagalume = "c882899b279d9a9627e078f427933b9b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_musicas);

        inicializaComponentes();
        configuraToolbar();

        //CONFIGURA RECYCLER
        recuperaMusicas();
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
        searchView = findViewById(R.id.materialSearchView);
    }

    //RECARREGA A LISTA COM TODAS AS MUSICAS
    private void recarregarMusicas() {
        adaptadorMusicas = new AdaptadorMusicas(this, musicas);
        recyclerView.setAdapter(adaptadorMusicas);
        adaptadorMusicas.notifyDataSetChanged();
    }

    //FILTA MUSICAS
    private void pesquisarMusicas(String texto) {
        List<Musica> musciasBusca = new ArrayList<>();

        //busca na lista de musicas e salva em uma outra lista
        for (Musica musica : musicas) {

            String nome = musica.getTitulo().toLowerCase();
            String cantor = musica.getArtista().toLowerCase();

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

        //SQLite
        try {
            //ABRIR BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //RECUPERAR
            Cursor cursor = bancoDeDados.rawQuery("SELECT titulo, artista, letra, id FROM musicas", null);

            //INDICES DA TABELA
            int indiceTitulo = cursor.getColumnIndex("titulo");
            int indiceArtista = cursor.getColumnIndex("artista");
            int indiceLetra = cursor.getColumnIndex("letra");
            int indiceId = cursor.getColumnIndex("id");

            //PERCORE TABELA
            int i = 0;
            cursor.moveToFirst();
            while (cursor != null) {

                String artista = cursor.getString(indiceArtista);
                String titulo = cursor.getString(indiceTitulo);
                String letra = cursor.getString(indiceLetra);
                Integer id = cursor.getInt(indiceLetra);

                musicas.add(new Musica(artista
                        , titulo
                        , letra
                        , id));

                cursor.moveToNext();
                i++;
//
//                if (i == 2)
//                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //CONVERTE IMAGENS SALVAS NOS RECURSOS EM BITMAP
    private Bitmap carregaImagem(int id) {
        return BitmapFactory
                .decodeResource(getResources(), id);
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
        Musica m = musicas.get(position);
//        BuscaMuscia buscaMusica = new BuscaMuscia(this);
//        String urlApi = "https://api.vagalume.com.br/search.php?art=" + m.getArtista() + "&mus=" + m.getNome() + "&apikey=" + chaveApiVagalume;
//        buscaMusica.execute(urlApi);
        Toast.makeText(this, m.getTitulo(), Toast.LENGTH_SHORT).show();
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









//    class BuscaMuscia extends AsyncTask<String, Void, Musica> {
//
//        ProgressDialog vrProgress = null;
//        private ListaMusicasActivity listaMusicasActivity = null;
//
//        public BuscaMuscia(ListaMusicasActivity listaMusicasActivity) {
//            this.listaMusicasActivity = listaMusicasActivity;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            vrProgress = new ProgressDialog(listaMusicasActivity);
//            vrProgress.setCancelable(false);
//            vrProgress.setCanceledOnTouchOutside(false);
//            vrProgress.setMessage("Carregando...");
//            vrProgress.setTitle("Agurde!");
//            vrProgress.show();
//        }
//
//        @Override
//        protected Musica doInBackground(String... strings) {
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
//                inputStreamReader = new InputStreamReader(inputStream);
//
//                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//                buffer = new StringBuffer();
//                String linha = "";
//
//                while ((linha = reader.readLine()) != null) {
//                    buffer.append(linha);
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
//            Musica musica = gson.fromJson(buffer.toString(), Musica.class);
//
//            return musica;
//        }
//
//        @Override
//        protected void onPostExecute(Musica musica) {
//            super.onPostExecute(musica);
//            vrProgress.dismiss();
//
//            Intent intencao = new Intent(listaMusicasActivity, MusicaActivity.class);
//            listaMusicasActivity.startActivity(intencao);
//        }
//    }
}
