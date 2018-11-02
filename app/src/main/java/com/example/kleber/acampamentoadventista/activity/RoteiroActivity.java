package com.example.kleber.acampamentoadventista.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
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

public class RoteiroActivity extends AppCompatActivity implements ValueEventListener {

    //NAVEGACAO
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //FIREBASE REFERENCE
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    //LISTA DE ROTEIROS
//    private List<Roteiro> roteiros = new ArrayList<>();

    //ROTEIROS
    private RoteiroSextaFragment roteiroSextaFragment;
    private RoteiroSabadoFragment roteiroSabadoFragment;
    private RoteiroDomingoFragment roteiroDomingoFragment;
    private RoteiroSegundaFragment roteiroSegundaFragment;
    private RoteiroTercaFragment roteiroTercaFragment;

    //DICIONARIO
    private Bundle dicionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiro);

        inicializaComponentes();
        configuraToolbar();

        //FIREBASE
        final DatabaseReference roteirosDb = referencia.child("roteiros");
        roteirosDb.addValueEventListener(this);

        //SQLite
        try {
            //ABRIR BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //RECUPERAR
            Cursor cursor = bancoDeDados.rawQuery("SELECT titulo, conteudo FROM roteiros", null);

            //INDICES DA TABELA
            int indiceTitulo = cursor.getColumnIndex("titulo");
            int indiceConteudo = cursor.getColumnIndex("conteudo");

            //PERCORE TABELA
            int i = 0;
            cursor.moveToFirst();
            while (cursor != null) {
                inicializaRoteiroFragment(i, dicionario, cursor.getString(indiceTitulo), cursor.getString(indiceConteudo));
                cursor.moveToNext();
                i++;

                if (i == 2)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // CRIO O ADAPTER
        AdaptadorDePaginas adapter = new AdaptadorDePaginas(getSupportFragmentManager());

        // SETO OS FRAGMENTOS NO ADPTER
        adapter.AddFragment(roteiroSextaFragment, getString(R.string.sexta));
        adapter.AddFragment(roteiroSabadoFragment, getString(R.string.sabado));

        // SETO O ADAPTER NA VIEW PAGE
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void inicializaRoteiroFragment(int posicao, Bundle dicionario, String titulo, String conteudo) {

        Roteiro r = null;

        switch (posicao){

            case 0:
                r = new Roteiro(titulo, conteudo);
                dicionario.putSerializable(Roteiros.SEXTA.name(), r);
                roteiroSextaFragment = new RoteiroSextaFragment();
                roteiroSextaFragment.setArguments(dicionario);
                break;
            case 1:
                r = new Roteiro(titulo, conteudo);
                dicionario.putSerializable(Roteiros.SABADO.name(), r);
                roteiroSabadoFragment = new RoteiroSabadoFragment();
                roteiroSabadoFragment.setArguments(dicionario);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;

        }

    }

    private void configuraToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void inicializaComponentes() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.pager);
        dicionario = new Bundle();
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
//        roteiros.add(new Roteiro((String) dataSnapshot.child("sexta").child("titulo").getValue(),
//                (String) dataSnapshot.child("sexta").child("conteudo").getValue()));
//
//        // dicionario
//        Bundle dicionario = new Bundle();
//
//        // fragmento sexta
//        RoteiroSextaFragment sextaFragment = new RoteiroSextaFragment();
//        dicionario.putSerializable("roteiro", roteiros.get(0));
//        sextaFragment.setArguments(dicionario);
//
//        // adiciona fragmentos
//        AdaptadorDePaginas adapter = new AdaptadorDePaginas(getSupportFragmentManager());
//        adapter.AddFragment(sextaFragment, getString(R.string.sexta));
//        adapter.AddFragment(new RoteiroSabadoFragment(), getString(R.string.sabado));
//        adapter.AddFragment(new RoteiroDomingoFragment(), getString(R.string.domingo));
//        adapter.AddFragment(new RoteiroSegundaFragment(), getString(R.string.segunda));
//        adapter.AddFragment(new RoteiroTercaFragment(), getString(R.string.terca));
//
//        // configura a ViewPagina e sincroniza com os Tabs
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
