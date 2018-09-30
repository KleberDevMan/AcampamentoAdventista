package com.example.kleber.acampamentoadventista.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorContato;
import com.example.kleber.acampamentoadventista.modelos.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Contato> contatos;
    private AdaptadorContato adaptadorContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        //inicializa componentes
        recyclerView = findViewById(R.id.lista_contatos);
        contatos = new ArrayList<>();

        //Configura RecyclerView
        recuperarContatos();
        adaptadorContato = new AdaptadorContato(this, contatos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorContato);
    }

    private void recuperarContatos() {

        contatos.add(new Contato("Kleber (desenvolvedor do app)","(63) 99250 - 4449"));
        contatos.add(new Contato("Isacc (organizador do evento)","(63) XXXX - XXXX"));
    }
}
