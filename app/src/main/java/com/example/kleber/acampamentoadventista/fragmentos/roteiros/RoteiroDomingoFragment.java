package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
import com.example.kleber.acampamentoadventista.modelos.roteiropojo.Roteiro;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoteiroDomingoFragment extends Fragment {


    public RoteiroDomingoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roteiro_fragmento_domingo, container, false);

        //REFERENCIO COMPONENTES
        TextView conteudo = view.findViewById(R.id.conteudo_roteiro_domingo);
        ImageView imageView = view.findViewById(R.id.image_roteiro_domingo);

        Bundle dicionario = getArguments();

        if (dicionario.size() != 0) {
            //PEGO OBJETO NO DICIONARIO DE DADOS
            Roteiro r = (Roteiro) dicionario.getSerializable(Roteiros.DOMINGO.name());

            //SETO DADOS NA VIEW
            conteudo.setText(r.getContent().replace("\\n", "\n"));
            Picasso.get().load(r.getUrlImage()).into(imageView);
        }
        return view;
    }

}
