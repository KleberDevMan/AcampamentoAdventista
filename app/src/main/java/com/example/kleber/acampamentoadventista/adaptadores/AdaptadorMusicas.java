package com.example.kleber.acampamentoadventista.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.modelos.musicapojo.Musica;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorMusicas extends RecyclerView.Adapter<AdaptadorMusicas.Holder>{

    private Context context;
    private List<Musica> musicas;

    //CONTRUTOR
    public AdaptadorMusicas(Context context, List<Musica> musicas) {
        this.context = context;
        this.musicas = musicas;
    }





    //CHAMADO PARA CRIAR OS ITENS
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View celula = LayoutInflater.
                from(parent.getContext())
                .inflate(R.layout.celula_musica, parent, false);
        //mandando o objeto para o holder setar dados
        Holder holder = new Holder(celula);
        return holder;
    }

    //AQUI EU CHAMO OS ITENS CRIADOR
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Musica musica = musicas.get(position);
        holder.titulo.setText(musica.getTitle());
        holder.artista.setText(musica.getArtist());

        try {
            Picasso.get().load(musica.getUrlImage()).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //RETORNO A QUANTIDADE DE ITENS QUE SERAO EXIBIDOR
    @Override
    public int getItemCount() {
        return musicas.size();
    }
















    //GUARDA OS DADOS DA MUSICA ANTES DE CRIALA
    public class Holder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView artista;
        CircleImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.celula_titulo);
            artista = itemView.findViewById(R.id.celula_artista);
            imageView = itemView.findViewById(R.id.celula_album);
        }
    }
}
