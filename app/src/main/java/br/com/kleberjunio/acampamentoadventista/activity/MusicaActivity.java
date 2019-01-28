package br.com.kleberjunio.acampamentoadventista.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.kleberjunio.acampamentoadventista.R;
import br.com.kleberjunio.acampamentoadventista.modelos.musicapojo.Musica;

public class MusicaActivity extends AppCompatActivity {

    private TextView titulo, artista, letra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        titulo = findViewById(R.id.musica_titulo);
        artista = findViewById(R.id.musica_artista);
        letra = findViewById(R.id.musica_letra);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        Musica m = (Musica) bundle.getSerializable("musica");

        titulo.setText(m.getTitle());
        artista.setText(m.getArtist());
        letra.setText(m.getLyric().replace("\\n", "\n"));

    }
}
