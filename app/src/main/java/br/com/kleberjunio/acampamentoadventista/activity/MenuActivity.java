package br.com.kleberjunio.acampamentoadventista.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.kleberjunio.acampamentoadventista.R;
import br.com.kleberjunio.acampamentoadventista.modelos.informepojo.Informe;
import br.com.kleberjunio.acampamentoadventista.modelos.musicapojo.Musica;
import br.com.kleberjunio.acampamentoadventista.modelos.roteiropojo.Roteiro;
import br.com.kleberjunio.acampamentoadventista.modelos.url_playlist_videos_pojo.UrlPlaylistVideo;

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
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    //WEB_SERVICE: MUSICAS
    private String urlMusicas = "https://fierce-inlet-45074.herokuapp.com/musics.json";

    //WEB_SERVICE: ROTEIROS
    private String urlRoteiros = "https://fierce-inlet-45074.herokuapp.com/scripts.json";

    //WEB_SERVICE: INFORMES
    private String urlInformes = "https://fierce-inlet-45074.herokuapp.com/infos.json";

    //WEB_SERVICE: LINK
    private String urlPlaylistVideos = "https://fierce-inlet-45074.herokuapp.com/link_playlist_videos.json";

    //Primerio acesso
//    private boolean primeiroAcesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        boolean primeiroAcesso = preferences.getBoolean("primeiroAcesso", true);

        if (primeiroAcesso) {

            buscaESalvaNoBancoPelaPrimeiraVez();

        } else {
            buscaDadosESalvaNaBaseLocal();

            Toast.makeText(this, "nao mais a primeira vez", Toast.LENGTH_SHORT).show();
        }

    }

    private void buscaESalvaNoBancoPelaPrimeiraVez() {

        //busquei e salvei no banco
        try {
            //CRIA/ABRE BANCO LOCAL
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //-------------------- ARMAZENAMENTO INICIAL -----------------------
            ThreadBuscaDadosESalvaNoBancoNoPrimeiroAcesso threadBuscaDadosESalvaNoBancoNoPrimeiroAcesso =
                    new ThreadBuscaDadosESalvaNoBancoNoPrimeiroAcesso(this, bancoDeDados);
            threadBuscaDadosESalvaNoBancoNoPrimeiroAcesso.execute(urlPlaylistVideos);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Toast.makeText(this, "PRIMEIRA VEZ", Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("primeiroAcesso", false);
        editor.apply();

    }


    //TREAD QUE BUSCA OS ROTEIROS
    class ThreadBuscaDadosESalvaNoBancoNoPrimeiroAcesso extends AsyncTask<String, Void, List<Roteiro>> {

        private AppCompatActivity activity = null;
        private SQLiteDatabase bancoDeDados;
        private ProgressDialog vrProgress = null;

        List<Roteiro> roteiros = null;
        List<Musica> musicas = null;
        List<Informe> informes = null;
        String urlVideos = null;


        public ThreadBuscaDadosESalvaNoBancoNoPrimeiroAcesso(AppCompatActivity activity, SQLiteDatabase bancoDeDados) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            vrProgress = new ProgressDialog(activity);
            vrProgress.setCancelable(false);
            vrProgress.setCanceledOnTouchOutside(false);
            vrProgress.setMessage("Carregando...");
            vrProgress.setTitle("Aguarde!");
            vrProgress.show();

        }

        @Override
        protected List<Roteiro> doInBackground(String... strings) {

            //----------- ROTEIROS -----------------------------------------------------------------
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;
            Gson gson = new Gson();

            try {
                URL url = new URL(urlRoteiros);
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
            }

            Type collectionTypeRoteiros = new TypeToken<List<Roteiro>>() {
            }.getType();

            try {
                roteiros = gson.fromJson(buffer.toString(), collectionTypeRoteiros);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //----------- MUSICAS -----------------------------------------------------------------
            try {
                URL url = new URL(urlMusicas);
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
            }

            Type collectionTypeMusicas = new TypeToken<List<Musica>>() {
            }.getType();

            try {
                musicas = gson.fromJson(buffer.toString(), collectionTypeMusicas);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //----------- INFORMES -----------------------------------------------------------------
            try {
                URL url = new URL(urlInformes);
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
            }

            Type collectionTypeInformes = new TypeToken<List<Informe>>() {
            }.getType();

            try {
                informes = gson.fromJson(buffer.toString(), collectionTypeInformes);
            } catch (Exception e) {
                e.printStackTrace();
            }


            //----------- URL VIDEOS -----------------------------------------------------------------
            try {
                URL url = new URL(urlPlaylistVideos);
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
            }

//            List<UrlPlaylistVideo> urlPlaylistVideos = null;
//
//            Type collectionTypeVideos = new TypeToken<List<UrlPlaylistVideo>>() {
//            }.getType();


//            List<UrlPlaylistVideo> urlPlaylistVideosPrimeiroAcesso = null;
//
//            Type collectionTypeVideosPrimeiroAcesso = new TypeToken<List<UrlPlaylistVideo>>() {
//            }.getType();
//
//            try {
//                urlPlaylistVideosPrimeiroAcesso = gson.fromJson(buffer.toString(), collectionTypeVideosPrimeiroAcesso);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (urlPlaylistVideosPrimeiroAcesso != null) {
//                urlVideos = urlPlaylistVideosPrimeiroAcesso.get(0).getLink();
//            }


            Gson gson2 = new Gson();

            List<UrlPlaylistVideo> urlPlaylistVideos2 = null;

            Type collectionType2 = new TypeToken<List<UrlPlaylistVideo>>() {
            }.getType();

            try {
                urlPlaylistVideos2 = gson2.fromJson(buffer.toString(), collectionType2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (urlPlaylistVideos2 != null) {
                urlVideos = urlPlaylistVideos2.get(0).getLink();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Roteiro> parametro) {
            super.onPostExecute(roteiros);

            //----------- ROTEIROS -----------------------------------------------------------------
            if (roteiros != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS roteiros");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS roteiros(id INTEGER, titulo VARCHAR, conteudo VARCHAR, url_imagem VARCHAR )");

                for (Roteiro r : roteiros) {
                    //INSERE MUSICA
                    bancoDeDados.execSQL("INSERT INTO roteiros(id, titulo, conteudo, url_imagem) VALUES('" + r.getId() + "', '" + r.getTitle() + "', '" + r.getContent() + "', '" + r.getUrlImage() + "') ");
                }
            }

            //----------- MUSICAS -----------------------------------------------------------------
            if (musicas != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS musicas");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS musicas ( titulo VARCHAR, artista VARCHAR, letra VARCHAR, id INTEGER, url_imagem VARCHAR)");

//                //APAGA MUSICAS
//                bancoDeDados.execSQL("DELETE FROM musicas;");

                for (Musica m : musicas) {
                    //INSERE MUSICA

                    bancoDeDados.execSQL("INSERT INTO musicas(titulo, artista, letra, id, url_imagem) VALUES('" + m.getTitle() + "', '" + m.getArtist() + "', '" + m.getLyric() + "', '" + m.getId() + "','" + m.getUrlImage() + "') ");
                }
            }

            //----------- INFORMES -----------------------------------------------------------------
            if (informes != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS informes");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS informes(id INTEGER, titulo VARCHAR, conteudo VARCHAR, url_imagem VARCHAR )");

                for (Informe informe : informes) {
                    //INSERE MUSICA
                    bancoDeDados.execSQL("INSERT INTO informes(id, titulo, conteudo, url_imagem) VALUES('" + informe.getId() + "', '" + informe.getTitle() + "', '" + informe.getContent() + "', '" + informe.getUrlImage() + "') ");
                }
            }

            //----------- URL LINK -----------------------------------------------------------------
            if (urlVideos != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS linkplaylistvideos");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS linkplaylistvideos(link VARCHAR)");
//
//                //APAGA TABELA
//                bancoDeDados.execSQL("DELETE FROM linkplaylistvideos;");

                //INSERE NA TABELA
                bancoDeDados.execSQL("INSERT INTO linkplaylistvideos(link) VALUES('" + urlVideos + "')");
            }

            vrProgress.dismiss();
        }
    }


    public void btnMusicas(View botao) {
        Intent intencao = new Intent(this, ListaMusicasActivity.class);
        this.startActivity(intencao);
    }

    public void btnRoteiro(View botao) {
        Intent intencao = new Intent(this, RoteirosActivity.class);
        this.startActivity(intencao);
    }

    public void btnNews(View botao) {
        Intent intencao = new Intent(this, ListaVideosActivity.class);
        this.startActivity(intencao);
    }

    public void btnMeditacao(View botao) {
        Intent intencao = new Intent(this, InformesActivity.class);
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

    //OUVINTE DOS BOTOES DO MENU 3 PONTINHOS
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.contatos:
                //CHAMA A TELA DE CONTATOS
                this.startActivity(new Intent(this, ContatosActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    //BUSCA DADOS NO WEBSERVICE E SALVA NO SQLITE
    public void buscaDadosESalvaNaBaseLocal() {

        //SQLite
        try {
            //CRIA/ABRE BANCO LOCAL
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //-------------------- BAIXA ROTEIROS -----------------------
            BuscaRoteiros buscaRoteiros = new BuscaRoteiros(this, bancoDeDados);
            buscaRoteiros.execute(urlRoteiros);

            //-------------------- BAIXA MUSICAS -----------------------
            BuscaMuscia buscaMusica = new BuscaMuscia(this, bancoDeDados);
            buscaMusica.execute(urlMusicas);

            //-------------------- BAIXA INFORMATIVOS -----------------------
            BuscaInformes buscaInformes = new BuscaInformes(this, bancoDeDados);
            buscaInformes.execute(urlInformes);

            //-------------------- ATUALIZA O LINK DA PLAYLIST DO YOUTUBE -----------------------
            BuscaLink buscaLink = new BuscaLink(this, bancoDeDados);
            buscaLink.execute(urlPlaylistVideos);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //TREAD QUE BUSCA OS ROTEIROS
    class BuscaRoteiros extends AsyncTask<String, Void, List<Roteiro>> {

        private AppCompatActivity activity = null;
        private SQLiteDatabase bancoDeDados;

        public BuscaRoteiros(AppCompatActivity activity, SQLiteDatabase bancoDeDados) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
        }

        @Override
        protected List<Roteiro> doInBackground(String... strings) {

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
            }

            Gson gson = new Gson();

            List<Roteiro> roteiros = null;

            Type collectionType = new TypeToken<List<Roteiro>>() {
            }.getType();

            try {
                roteiros = gson.fromJson(buffer.toString(), collectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (roteiros == null)
                return null;
            else
                return roteiros;
        }

        @Override
        protected void onPostExecute(List<Roteiro> roteiros) {
            super.onPostExecute(roteiros);

            if (roteiros != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS roteiros");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS roteiros(id INTEGER, titulo VARCHAR, conteudo VARCHAR, url_imagem VARCHAR )");

                for (Roteiro r : roteiros) {
                    //INSERE MUSICA
                    bancoDeDados.execSQL("INSERT INTO roteiros(id, titulo, conteudo, url_imagem) VALUES('" + r.getId() + "', '" + r.getTitle() + "', '" + r.getContent() + "', '" + r.getUrlImage() + "') ");
                }
            }
        }
    }

    //TREAD QUE BUSCA A LETRA DAS MUSICAS
    class BuscaMuscia extends AsyncTask<String, Void, List<Musica>> {

        private AppCompatActivity activity = null;
        private SQLiteDatabase bancoDeDados;

        public BuscaMuscia(AppCompatActivity activity, SQLiteDatabase bancoDeDados) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
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
            }

            Gson gson = new Gson();

            List<Musica> musicas = null;

            Type collectionType = new TypeToken<List<Musica>>() {
            }.getType();

            try {
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

            if (musicas != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS musicas");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS musicas ( titulo VARCHAR, artista VARCHAR, letra VARCHAR, id INTEGER, url_imagem VARCHAR)");

//                //APAGA MUSICAS
//                bancoDeDados.execSQL("DELETE FROM musicas;");

                for (Musica m : musicas) {
                    //INSERE MUSICA
                    bancoDeDados.execSQL("INSERT INTO musicas(titulo, artista, letra, id, url_imagem) VALUES('" + m.getTitle() + "', '" + m.getArtist() + "', '" + m.getLyric() + "', '" + m.getId() + "','" + m.getUrlImage() + "') ");
                }
            }
        }
    }

    //TREAD QUE BUSCA OS INFORMATIVOS
    class BuscaInformes extends AsyncTask<String, Void, List<Informe>> {

        private AppCompatActivity activity = null;
        private SQLiteDatabase bancoDeDados;

        public BuscaInformes(AppCompatActivity activity, SQLiteDatabase bancoDeDados) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
        }

        @Override
        protected List<Informe> doInBackground(String... strings) {

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
            }

            Gson gson = new Gson();

            List<Informe> informes = null;

            Type collectionType = new TypeToken<List<Informe>>() {
            }.getType();

            try {
                informes = gson.fromJson(buffer.toString(), collectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (informes == null)
                return null;
            else
                return informes;
        }

        @Override
        protected void onPostExecute(List<Informe> informes) {
            super.onPostExecute(informes);

            if (informes != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS informes");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS informes(id INTEGER, titulo VARCHAR, conteudo VARCHAR, url_imagem VARCHAR )");

                for (Informe informe : informes) {
                    //INSERE MUSICA
                    bancoDeDados.execSQL("INSERT INTO informes(id, titulo, conteudo, url_imagem) VALUES('" + informe.getId() + "', '" + informe.getTitle() + "', '" + informe.getContent() + "', '" + informe.getUrlImage() + "') ");
                }
            }
        }
    }

    //TREAD QUE BUSCA E ATUALIZA O LINK
    class BuscaLink extends AsyncTask<String, Void, String> {

        private AppCompatActivity activity = null;
        private SQLiteDatabase bancoDeDados;

        public BuscaLink(AppCompatActivity activity, SQLiteDatabase bancoDeDados) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
        }

        @Override
        protected String doInBackground(String... strings) {

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
            }

            Gson gson = new Gson();

            List<UrlPlaylistVideo> urlPlaylistVideos = null;

            Type collectionType = new TypeToken<List<UrlPlaylistVideo>>() {
            }.getType();

            try {
                urlPlaylistVideos = gson.fromJson(buffer.toString(), collectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (urlPlaylistVideos == null)
                return null;
            else
                return urlPlaylistVideos.get(0).getLink();
        }

        @Override
        protected void onPostExecute(String linkPlaylistVideos) {
            super.onPostExecute(linkPlaylistVideos);

            if (linkPlaylistVideos != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS linkplaylistvideos");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS linkplaylistvideos(link VARCHAR)");
//
//                //APAGA TABELA
//                bancoDeDados.execSQL("DELETE FROM linkplaylistvideos;");

                //INSERE NA TABELA
                bancoDeDados.execSQL("INSERT INTO linkplaylistvideos(link) VALUES('" + linkPlaylistVideos + "')");
            }
        }
    }
}
