package com.example.kleber.acampamentoadventista.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.adaptadores.AdaptadorMiniaturas;
import com.example.kleber.acampamentoadventista.listeners.RecyclerItemClickListener;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

public class MolduraActivity extends AppCompatActivity {

    //chamado sempre que a classe é carregada
    static {
        //Inicializa a biblioteca de filtros
        System.loadLibrary("NativeImageProcessor");
    }

    private ImageView foto;
    private Bitmap imagemOriginal;
    private Bitmap imagemFiltro;
    private List<ThumbnailItem> listFiltros;

    private RecyclerView recyclerFiltros;
    private AdaptadorMiniaturas adaptadorMiniaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moldura);

        //Inicializa componentes
        foto = findViewById(R.id.foto_escolhida);
        listFiltros = new ArrayList<>();
        recyclerFiltros = findViewById(R.id.recycler_molduras);

        //configura a toolbar
        configuraToolbar();

        //Recupera foto escolhida pelo usuario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] dadosImagem = bundle.getByteArray("foto");
            imagemOriginal = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
            foto.setImageBitmap(imagemOriginal);

            //Configura recyclerview de Filtros
            adaptadorMiniaturas = new AdaptadorMiniaturas(getApplicationContext(), listFiltros);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false);
            recyclerFiltros.setLayoutManager(layoutManager);
            recyclerFiltros.setAdapter(adaptadorMiniaturas);

            //Recupera os filtros
            recuperarFiltros();

            //Adiciona evento de clique no recyclerView de Miniaturas
            recyclerFiltros.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),
                            recyclerFiltros,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    ThumbnailItem item = listFiltros.get(position);

                                    //copia da imagem original
                                    imagemFiltro = imagemOriginal.copy(imagemOriginal.getConfig(), true);
                                    Filter filter = item.filter;
                                    //seta foto com mudança
                                    foto.setImageBitmap(filter.processFilter(imagemFiltro));

                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            }
                    )
            );
        }
    }

    private void recuperarFiltros() {

        //Limpa itens
        ThumbnailsManager.clearThumbs();
        listFiltros.clear();

        //Configura filtro normal
        ThumbnailItem item = new ThumbnailItem();
        item.image = imagemOriginal;
        item.filterName = "Normal";
        ThumbnailsManager.addThumb(item);

        List<Filter> filters = FilterPack.getFilterPack(getApplicationContext());
        for (Filter filtro : filters) {

            ThumbnailItem itemFiltro = new ThumbnailItem();
            itemFiltro.image = imagemOriginal;
            itemFiltro.filter = filtro;
            itemFiltro.filterName = filtro.getName();
            //adicionando miniaturas
            ThumbnailsManager.addThumb(itemFiltro);
        }

        //Seto uma lista de imagens com filtro na lista de filtros
        listFiltros.addAll(ThumbnailsManager.processThumbs(getApplicationContext()));

        //atualiza o adptador de miniaturas
        adaptadorMiniaturas.notifyDataSetChanged();

    }

    private void configuraToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Molduras");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_close_branco_24dp);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_publicar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_publicar:
                Toast.makeText(this, "Publicando na rede social preferida", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
