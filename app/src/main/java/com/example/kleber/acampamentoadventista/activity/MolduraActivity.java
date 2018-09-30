package com.example.kleber.acampamentoadventista.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.kleber.acampamentoadventista.R;

public class MolduraActivity extends AppCompatActivity {

    private ImageView foto;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moldura);

        //        configuraToolbar();

        //Recupera foto escolhida pelo usuario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] dadosImagem = bundle.getByteArray("foto");
            imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
            foto.setImageBitmap(imagem);
        }
    }

    private void configuraToolbar() {
        if (getSupportActionBar() != null) {
//
//            Toolbar toolbar = findViewById(R.id.toolbar);
//            toolbar.setTitle("");
            getSupportActionBar().setTitle("");
            //seta btnVoltar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_close_branco_24dp);
        }
    }

    private void inicializarComponentes() {
        foto = findViewById(R.id.foto_escolhida);
    }
}
