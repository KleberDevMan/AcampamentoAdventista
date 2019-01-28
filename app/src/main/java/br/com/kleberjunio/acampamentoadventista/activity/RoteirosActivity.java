package br.com.kleberjunio.acampamentoadventista.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.kleberjunio.acampamentoadventista.R;
import br.com.kleberjunio.acampamentoadventista.activity.enuns.Roteiros;
import br.com.kleberjunio.acampamentoadventista.adaptadores.AdaptadorDePaginas;
import br.com.kleberjunio.acampamentoadventista.fragmentos.roteiros.RoteiroDomingoFragment;
import br.com.kleberjunio.acampamentoadventista.fragmentos.roteiros.RoteiroSabadoFragment;
import br.com.kleberjunio.acampamentoadventista.fragmentos.roteiros.RoteiroSegundaFragment;
import br.com.kleberjunio.acampamentoadventista.fragmentos.roteiros.RoteiroSextaFragment;
import br.com.kleberjunio.acampamentoadventista.fragmentos.roteiros.RoteiroTercaFragment;
import br.com.kleberjunio.acampamentoadventista.modelos.roteiropojo.Roteiro;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class RoteirosActivity extends AppCompatActivity {

    //NAVEGACAO
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //ADAPTER PARA OS FRAGMENTS
    AdaptadorDePaginas adapter;

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

//        alteraTabLayout();

        //SQLite
        try {
            //ABRIR BANCO
            SQLiteDatabase bancoDeDados = openOrCreateDatabase("app"
                    , MODE_PRIVATE, null);

            //RECUPERAR
            Cursor cursor = bancoDeDados.rawQuery("SELECT titulo, conteudo, url_imagem FROM roteiros ORDER BY id ASC", null);

            //INDICES DA TABELA
            int indiceTitulo = cursor.getColumnIndex("titulo");
            int indiceConteudo = cursor.getColumnIndex("conteudo");
            int indiceUrlImagem = cursor.getColumnIndex("url_imagem");

            //PERCORE TABELA
            int i = 0;
            cursor.moveToFirst();
            while (cursor != null) {
                inicializaRoteiroFragment(i
                        , dicionario
                        , cursor.getString(indiceTitulo)
                        , cursor.getString(indiceConteudo)
                        , cursor.getString(indiceUrlImagem));

                cursor.moveToNext();
                i++;

                //////////// TIRAR!!!!
                //QTD DE FRAGMENTOS PRONTOS
                if (i == 5) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // CRIO O ADAPTER
        adapter = new AdaptadorDePaginas(getSupportFragmentManager());

        // SETO OS FRAGMENTOS NO ADPTER
        adapter.AddFragment(roteiroSextaFragment, getString(R.string.sexta));
        adapter.AddFragment(roteiroSabadoFragment, getString(R.string.sabado));
        adapter.AddFragment(roteiroDomingoFragment, getString(R.string.domingo));
        adapter.AddFragment(roteiroSegundaFragment, getString(R.string.segunda));
        adapter.AddFragment(roteiroTercaFragment, getString(R.string.terca));


        // SETO O ADAPTER NA VIEW PAGE
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void alteraTabLayout() {
        // Mario Velasco's code
        tabLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                int tabLayoutWidth = tabLayout.getWidth();

                DisplayMetrics metrics = new DisplayMetrics();
                RoteirosActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int deviceWidth = metrics.widthPixels;

                if (tabLayoutWidth < deviceWidth)
                {
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                } else
                {
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    tabLayout.setPadding(30,0,30,0);
                }
            }
        });
    }

    private void configuraToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(1);
        }
    }
    private void inicializaComponentes() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.pager);
        dicionario = new Bundle();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        alteraTabLayout();
    }

    //INICIALIZO O FRAGMENTO ROTEIRO COM DADOS DO SQLITE
    private void inicializaRoteiroFragment(int posicao, Bundle dicionario, String titulo, String conteudo, String url_imagem) {

        Roteiro r = null;

        switch (posicao){

            case 0:
                r = new Roteiro(titulo, conteudo, url_imagem);
                dicionario.putSerializable(Roteiros.SEXTA.name(), r);
                roteiroSextaFragment = new RoteiroSextaFragment();
                roteiroSextaFragment.setArguments(dicionario);
                break;
            case 1:
                r = new Roteiro(titulo, conteudo, url_imagem);
                dicionario.putSerializable(Roteiros.SABADO.name(), r);
                roteiroSabadoFragment = new RoteiroSabadoFragment();
                roteiroSabadoFragment.setArguments(dicionario);
                break;
            case 2:
                r = new Roteiro(titulo, conteudo, url_imagem);
                dicionario.putSerializable(Roteiros.DOMINGO.name(), r);
                roteiroDomingoFragment = new RoteiroDomingoFragment();
                roteiroDomingoFragment.setArguments(dicionario);
                break;
            case 3:
                r = new Roteiro(titulo, conteudo, url_imagem);
                dicionario.putSerializable(Roteiros.SEGUNDA.name(), r);
                roteiroSegundaFragment = new RoteiroSegundaFragment();
                roteiroSegundaFragment.setArguments(dicionario);
                break;
            case 4:
                r = new Roteiro(titulo, conteudo, url_imagem);
                dicionario.putSerializable(Roteiros.TERCA.name(), r);
                roteiroTercaFragment = new RoteiroTercaFragment();
                roteiroTercaFragment.setArguments(dicionario);
                break;
            case 5:
                break;
        }

    }
}
