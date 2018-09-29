package com.example.kleber.acampamentoadventista.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorMusicas;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.example.kleber.acampamentoadventista.modelos.Musica;

import java.util.ArrayList;
import java.util.List;

public class ListaMusicasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Musica> musicas;
    private AdaptadorMusicas adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_musicas);

        musicas = getMusicarBanco();

        //INSTACIA O ADAPTER
        adaptador = new AdaptadorMusicas(this, musicas);

        //OBTEM A REFERENCIA PARA O RECYCLE
        recyclerView = findViewById(R.id.lista_musicas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //CONFIGURA O ADAPTER DO RECYCLE
        recyclerView.setAdapter(adaptador);

        //EVENTO DE CLICK
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
    }

    private List<Musica> getMusicarBanco() {
        List<Musica> m = new ArrayList<Musica>();
        m.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        m.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        m.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        m.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        m.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        m.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        m.add(new Musica("Falar com Deus", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        m.add(new Musica("Santo dia do Senhor", "letra", "ministério jovem", carregaImagem(R.drawable.download)));
        m.add(new Musica("Dez mil razões", "letra", "ministério jovem", carregaImagem(R.drawable.salvacaoeservico1)));
        return m;
    }

    private Bitmap carregaImagem(int id) {
        return BitmapFactory
                .decodeResource(getResources(), id);
    }
}
