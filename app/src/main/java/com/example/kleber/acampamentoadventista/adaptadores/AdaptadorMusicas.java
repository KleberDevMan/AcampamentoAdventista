package com.example.kleber.acampamentoadventista.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.modelos.Musica;

import org.w3c.dom.Text;

import java.util.List;

public class AdaptadorMusicas extends RecyclerView.Adapter<AdaptadorMusicas.Holder>{

    private Context context;
    private List<Musica> musicas;

    public AdaptadorMusicas(Context context, List<Musica> musicas) {
        this.context = context;
        this.musicas = musicas;
    }

    //GUARDA OS DADOS DA MUSICA ANTES DE CRIALA
    public class Holder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView letra;
        TextView cantor;
        ImageView album;

        public Holder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.celula_nome);
            cantor = itemView.findViewById(R.id.celula_cantor);
            album = itemView.findViewById(R.id.celula_album);
        }
    }

    //CHAMADO PARA CRIAR OS ITENS
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //convertendo o xml da celula para um objeto
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
        holder.nome.setText(musica.getNome());
        holder.cantor.setText(musica.getCantor());
        holder.album.setImageBitmap(musica.getAlbum());
    }
    //RETORNO A QUANTIDADE DE ITENS QUE SERAO EXIBIDOR
    @Override
    public int getItemCount() {
        return musicas.size();
    }


}
