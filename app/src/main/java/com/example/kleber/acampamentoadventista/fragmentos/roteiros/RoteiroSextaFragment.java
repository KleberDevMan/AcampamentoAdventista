package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;

import com.example.kleber.acampamentoadventista.R;

public class RoteiroSextaFragment extends RoteiroFragment {


    public RoteiroSextaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roteiro_fragmento, container, false);

        titulo = view.findViewById(R.id.titulo_roteiro);
        conteudo = view.findViewById(R.id.conteudo_roteiro);

        dicionario = getArguments();

        int i = dicionario.size();

        if (dicionario.size() != 0) {
            Roteiro r = (Roteiro) dicionario.getSerializable(Roteiros.SEXTA.name());

            titulo.setText(r.getTitulo());
            conteudo.setText(r.getConteudo());
        }
        return view;

    }

}
