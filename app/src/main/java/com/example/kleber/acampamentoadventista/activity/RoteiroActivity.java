package com.example.kleber.acampamentoadventista.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorDePaginas;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroDomingoFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroSabadoFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroSegundaFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroSextaFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroTercaFragment;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoteiroActivity extends AppCompatActivity implements ValueEventListener{

    //Usados para navegacao
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //referencia ao banco de dados
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    //Lista de roteiros
    private List<Roteiro> roteiros = new ArrayList<>();
    //Lista de fregments
    private List<Fragment> fragmentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiro);

        //inicializa componentes
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.pager);

        //configura toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Busco roteiros
        final DatabaseReference roteirosDb = referencia.child( "roteiros" );
        roteirosDb.addValueEventListener(this);
    }

    public Roteiro getRoteiro(DataSnapshot snapshot) {
        Roteiro roteiro = new Roteiro();
        roteiro.setTitulo((String) snapshot.child("titulo").getValue());
        roteiro.setConteudo((String) snapshot.child("conteudo").getValue());
        return roteiro;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // se for a seta voltar
        if (item.getItemId() == android.R.id.home) {
            finish(); // fecha esta atividade e retorna à atividade de anterior (se houver)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // obetenho roteiro
        roteiros.add(new Roteiro((String)dataSnapshot.child("sexta").child("titulo").getValue(),
                (String)dataSnapshot.child("sexta").child("conteudo").getValue()));

        // dicionario
        Bundle dicionario = new Bundle();

        // fragmento sexta
        RoteiroSextaFragment sextaFragment = new RoteiroSextaFragment();
        dicionario.putSerializable("roteiro", roteiros.get(0));
        sextaFragment.setArguments(dicionario);

        // adiciona fragmentos
        AdaptadorDePaginas adapter = new AdaptadorDePaginas(getSupportFragmentManager());
        adapter.AddFragment(sextaFragment, getString(R.string.sexta));
        adapter.AddFragment(new RoteiroSabadoFragment(), getString(R.string.sabado));
        adapter.AddFragment(new RoteiroDomingoFragment(), getString(R.string.domingo));
        adapter.AddFragment(new RoteiroSegundaFragment(), getString(R.string.segunda));
        adapter.AddFragment(new RoteiroTercaFragment(), getString(R.string.terca));

        // configura a ViewPagina e sincroniza com os Tabs
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
