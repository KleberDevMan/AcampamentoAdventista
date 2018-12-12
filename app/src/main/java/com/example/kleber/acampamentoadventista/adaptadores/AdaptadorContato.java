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
import com.example.kleber.acampamentoadventista.modelos.contactpojo.Contato;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        holder.nome.setText( contato.getDescription());
        holder.numero.setText( contato.getNumber());

        try {
            Picasso.get().load(contato.getLinkImage()).into(holder.imagem);
        }catch (Exception e){
            Toast.makeText(context, "Erro ao carregar imagem(ns).", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView numero;
        CircleImageView imagem;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.celula_nome_contato);
            numero = itemView.findViewById(R.id.celula_numero_contato);
            imagem = itemView.findViewById(R.id.imagem_contato);
        }
    }

}

