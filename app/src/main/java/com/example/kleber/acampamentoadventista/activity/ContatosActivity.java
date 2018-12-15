package com.example.kleber.acampamentoadventista.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorContato;
import com.example.kleber.acampamentoadventista.modelos.contactpojo.Contato;
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
import java.util.ArrayList;
import java.util.List;

public class ContatosActivity extends AppCompatActivity {

    //WEB_SERVICE: CONTATOS
    private String urlContatos = "https://fierce-inlet-45074.herokuapp.com/contacts.json";

    private RecyclerView recyclerView;
    private List<Contato> contatos;
    private AdaptadorContato adaptadorContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        //INICIALIZA COMPONENTES
        recyclerView = findViewById(R.id.lista_contatos);
        contatos = new ArrayList<>();

        //CONFIGURA TOOLBAR
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //CARREGA CONTATOS
        carregaContatos();
    }

    private void carregaContatos() {
        //BUSCA NO WEBSERVICE E SALVA NO BANCO LOCAL
        buscaDadosESalvaNaBaseLocal();

        //PEGA OS DADOS DO BANCO E COLOCA EM MEMORIA
        recuperarContatos();

        //CONFIGURA RECYCLER
        adaptadorContato = new AdaptadorContato(this, contatos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorContato);
    }

    private void recuperarContatos() {
        //SQLite
        try {
            //LIMPO LISTA
            contatos.clear();

            //ABRIR BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //RECUPERAR
            Cursor cursor = bancoDeDados.rawQuery("SELECT id, description, number, email, url_imagem FROM contatos ORDER BY id ASC", null);

            //INDICES DA TABELA
            int indiceDescription = cursor.getColumnIndex("description");
            int indiceNumero = cursor.getColumnIndex("number");
            int indiceEmail = cursor.getColumnIndex("email");
            int indiceUrlImagem = cursor.getColumnIndex("url_imagem");

            //PERCORE TABELA
            int i = 0;
            cursor.moveToFirst();
            while (cursor != null) {

                String numero = cursor.getString(indiceNumero);
                String descricao = cursor.getString(indiceDescription);
                String email = cursor.getString(indiceEmail);
                String urlImage = cursor.getString(indiceUrlImagem);

                contatos.add(new Contato(descricao, numero, email, urlImage));

                cursor.moveToNext();
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // se for a seta voltar
        if (item.getItemId() == android.R.id.home) {
            finish(); // fecha esta atividade e retorna à atividade de anterior (se houver)
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

            //-------------------- BAIXA CONTATOS -----------------------
            BuscaContatos buscaContatos = new BuscaContatos(this, bancoDeDados);
            buscaContatos.execute(urlContatos);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //TREAD QUE BUSCA OS CONTATOS
    class BuscaContatos extends AsyncTask<String, Void, List<Contato>> {

        private AppCompatActivity activity;
        private SQLiteDatabase bancoDeDados;

        public BuscaContatos(AppCompatActivity activity, SQLiteDatabase bancoDeDados) {
            this.activity = activity;
            this.bancoDeDados = bancoDeDados;
        }

        @Override
        protected List<Contato> doInBackground(String... strings) {

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

            List<Contato> contatos = null;

            Type collectionType = new TypeToken<List<Contato>>() {
            }.getType();

            try {
                contatos = gson.fromJson(buffer.toString(), collectionType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (contatos == null)
                return null;
            else
                return contatos;
        }

        @Override
        protected void onPostExecute(List<Contato> contatos) {
            super.onPostExecute(contatos);

            if (contatos != null) {
                bancoDeDados.execSQL("DROP TABLE IF EXISTS contatos");

                //CRIA TABELA
                bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS contatos(id INTEGER, description VARCHAR, email VARCHAR, number VARCHAR, url_imagem VARCHAR )");

                //APAGA TABELA
                bancoDeDados.execSQL("DELETE FROM contatos;");

                for (Contato contato : contatos) {
                    //INSERE CONTATO
                    bancoDeDados.execSQL("INSERT INTO contatos(id, description, number, email, url_imagem) VALUES('" + contato.getId() + "', '" + contato.getDescription() + "', '" + contato.getNumber() + "', '" + contato.getEmail() + "', '" + contato.getLinkImage() + "') ");
                }
            }
        }
    }

}
