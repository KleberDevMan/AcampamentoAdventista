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

public class RoteiroActivity extends AppCompatActivity {

    //NAVEGACAO
    private TabLayout tabLayout;
    private ViewPager viewPager;

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


    //INICIALIZO O FRAGMENTO ROTEIRO COM DADOS DO SQLITE
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
}
