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
import com.example.kleber.acampamentoadventista.modelos.Video;

import java.util.List;

public class AdaptadorVideo extends RecyclerView.Adapter<AdaptadorVideo.MyViewHolder> {

    private List<Video> videos;
    private Context context;

    public AdaptadorVideo(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflar o layout da celula
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celula_video, parent, false);
        return new MyViewHolder(view);
    }

    //transformando o objeto em celula
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Video video = videos.get( position );
        holder.titulo.setText( video.getTitulo() );

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView descricao;
        TextView data;
        ImageView capa;

        public MyViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textTituloVideo);
            capa = itemView.findViewById(R.id.imageCapaVideo);
        }
    }

}
