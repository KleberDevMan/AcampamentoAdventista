package com.example.kleber.acampamentoadventista.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorDePaginas;
import com.example.kleber.acampamentoadventista.fragmentos.meditacoes.MeditacaoSabadoFragment;
import com.example.kleber.acampamentoadventista.fragmentos.meditacoes.MeditacaoSextaFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroDomingoFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroSabadoFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroSegundaFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroSextaFragment;
import com.example.kleber.acampamentoadventista.fragmentos.roteiros.RoteiroTercaFragment;

public class MeditacaoActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditacao);

        //inicializa componentes
        tabLayout = findViewById(R.id.tablayoutMeditacao);
        viewPager = findViewById(R.id.pagerMeditacao);

        //adiciona fragmentos
        AdaptadorDePaginas adapter = new AdaptadorDePaginas(getSupportFragmentManager());
        adapter.AddFragment(new MeditacaoSextaFragment(), getString(R.string.sexta));
        adapter.AddFragment(new MeditacaoSabadoFragment(), getString(R.string.sabado));

        //configura a ViewPagina e sincroniza com os Tabs
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
