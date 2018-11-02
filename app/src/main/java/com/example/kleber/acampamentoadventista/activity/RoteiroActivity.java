package com.example.kleber.acampamentoadventista.activity;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorDePaginas;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroDomingoFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroFragment;
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

public class RoteiroActivity extends AppCompatActivity implements ValueEventListener {

    //Usados para navegacao
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //referencia ao banco de dados
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    //Lista de roteiros
    private List<Roteiro> roteiros = new ArrayList<>();
    //Lista de fregments
    private List<RoteiroFragment> roteiroFragments = new ArrayList<>();

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

        //FIREBASE
        final DatabaseReference roteirosDb = referencia.child("roteiros");
        roteirosDb.addValueEventListener(this);

        //SQLite
        try {
            //CRIA BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //CRIA TABELA
            bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS roteiros ( titulo VARCHAR, conteudo VARCHAR )");

            bancoDeDados.execSQL("DELETE FROM roteiros;");

            //INSERIR DADOS
            bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Sexta', 'Conteudo vindo do SQLite - Sexta') ");
            bancoDeDados.execSQL("INSERT INTO roteiros(titulo, conteudo) VALUES('Sabado', 'Conteudo vindo do SQLite - Sabado') ");

            //RECUPERAR
            Cursor cursor = bancoDeDados.rawQuery("SELECT titulo, conteudo FROM roteiros", null);

            //INDICES DA TABELA
            int indiceTitulo = cursor.getColumnIndex("titulo");
            int indiceConteudo = cursor.getColumnIndex("conteudo");

            int i = 0;
            cursor.moveToFirst();
            while (cursor != null) {
//                Log.i("RESULTADO - nome: ", cursor.getString(indiceTitulo));
//                Log.i("RESULTADO - conteudo: ", cursor.getString(indiceConteudo));
                roteiros.add(new Roteiro(cursor.getString(indiceTitulo), cursor.getString(indiceConteudo)));
                Log.i("RESULTADO - ", roteiros.get(i).toString());
                cursor.moveToNext();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //SETA O ROTEIRO NA TELA -----------------------

        // CRIO UM DICIONARIO
        Bundle dicionario = new Bundle();

        // CRIA FRAGEMENTO
//        Roteiro r = new Roteiro("", "");
//        for (int i = 0; i < roteiros.size(); i++) {
//            roteiroFragments.add(criaFragemntoRoteiro(roteiros.get(i), dicionario));
//        }

        // CRIO O ADAPTER
        AdaptadorDePaginas adapter = new AdaptadorDePaginas(getSupportFragmentManager());

        // SETO OS FRAGMENTOS NO ADPTER
        adapter.AddFragment(criaFragemntoRoteiro(roteiros.get(0), dicionario), getString(R.string.sexta));
        adapter.AddFragment(criaFragemntoRoteiro(roteiros.get(1), dicionario), getString(R.string.sabado));
        adapter.AddFragment(new RoteiroDomingoFragment(), getString(R.string.domingo));
        adapter.AddFragment(new RoteiroSegundaFragment(), getString(R.string.segunda));
        adapter.AddFragment(new RoteiroTercaFragment(), getString(R.string.terca));

        for (int i = 0; i < roteiroFragments.size(); i++) {
            adapter.AddFragment(roteiroFragments.get(i), getString(R.string.sexta));
        }

        // SETO O ADAPTER NA VIEW PAGE
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        dicionario.remove("roteiro");
        // FIM -----------------------------------------
    }

    //Recebe um Model Roteiro e devolve um Frament Roteiro
    RoteiroFragment criaFragemntoRoteiro(Roteiro roteiro, Bundle dicionario) {
        RoteiroFragment roteiroFragment = new RoteiroFragment();
//        if (dicionario.getSerializable("roteiro")!=null){
//            dicionario.remove("roteiro");
//        }
        dicionario.putSerializable("roteiro", roteiro);
        roteiroFragment.setArguments(dicionario);
        return roteiroFragment;
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
        roteiros.add(new Roteiro((String) dataSnapshot.child("sexta").child("titulo").getValue(),
                (String) dataSnapshot.child("sexta").child("conteudo").getValue()));

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
        Toast.makeText(this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
