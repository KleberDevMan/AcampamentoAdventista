package com.example.kleber.acampamentoadventista.fragmentos.informes;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.activity.enuns.Informes;
import com.example.kleber.acampamentoadventista.modelos.informepojo.Informe;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegrasFragment extends Fragment {


    public RegrasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regras, container, false);

        //REFERENCIO COMPONENTES
        TextView conteudo = view.findViewById(R.id.conteudo_regras);
        ImageView imageView = view.findViewById(R.id.image_regras);

        Bundle dicionario = getArguments();

        if (dicionario.size() != 0) {
            //PEGO OBJETO NO DICIONARIO DE DADOS
            Informe informe = (Informe) dicionario.getSerializable(Informes.REGRAS.name());

            //SETO DADOS NA VIEW
            conteudo.setText(informe.getContent().replace("\\n", "\n"));
            Picasso.get().load(informe.getUrlImage()).into(imageView);
        }

        return view;
    }

}
