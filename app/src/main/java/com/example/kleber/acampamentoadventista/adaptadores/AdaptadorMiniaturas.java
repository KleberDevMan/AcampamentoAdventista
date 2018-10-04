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
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

public class AdaptadorMiniaturas extends RecyclerView.Adapter<AdaptadorMiniaturas.MyViewHolder> {

    private List<ThumbnailItem> listFiltros;
    private Context context;

    public AdaptadorMiniaturas(Context context, List<ThumbnailItem> listFiltros) {
        this.listFiltros = listFiltros;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.celula_filtro, parent, false);
        return new AdaptadorMiniaturas.MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ThumbnailItem item = listFiltros.get( position );

        holder.miniatua.setImageBitmap( item.image );
        holder.nomeFiltro.setText( item.filterName );

    }

    @Override
    public int getItemCount() {
        return listFiltros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView miniatua;
        TextView nomeFiltro;

        public MyViewHolder(View itemView){
            super(itemView);

            miniatua = itemView.findViewById(R.id.image_miniatura_filtro);
            nomeFiltro = itemView.findViewById(R.id.text_nome_filtro);

        }

    }

}
