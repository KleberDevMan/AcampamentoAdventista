package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoteiroSabadoFragment extends RoteiroFragment {


    public RoteiroSabadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roteiro_fragmento_sabado, container, false);

        titulo = view.findViewById(R.id.titulo_sabado);
        conteudo = view.findViewById(R.id.conteudo_sabado);

        dicionario = getArguments();

        int i = dicionario.size();

        if (dicionario.size() != 0) {
            Roteiro r = (Roteiro) dicionario.getSerializable(Roteiros.SABADO.name());

            titulo.setText(r.getTitulo());
            conteudo.setText(r.getConteudo());
        }
        return view;
    }


}
