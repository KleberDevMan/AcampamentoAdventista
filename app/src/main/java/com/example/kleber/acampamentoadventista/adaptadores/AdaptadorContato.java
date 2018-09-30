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
import com.example.kleber.acampamentoadventista.modelos.Contato;
import com.example.kleber.acampamentoadventista.modelos.Video;

import java.util.List;

public class AdaptadorContato extends RecyclerView.Adapter<AdaptadorContato.MyViewHolder> {

    private List<Contato> contatos;
    private Context context;

    public AdaptadorContato(Context context, List<Contato> contatos) {
        this.contatos= contatos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflar o layout da celula
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celula_contato, parent, false);
        return new MyViewHolder(view);
    }

    //transformando o objeto em celula
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Contato contato = contatos.get( position );
        holder.nome.setText( contato.getNome() );
        holder.numero.setText( contato.getNumero() );

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView numero;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.celula_nome_contato);
            numero = itemView.findViewById(R.id.celula_numero_contato);
        }
    }

}

