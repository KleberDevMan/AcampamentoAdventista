package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleber.acampamentoadventista.activity.enuns.Roteiros;
import com.example.kleber.acampamentoadventista.modelos.roteiropojo.Roteiro;

import com.example.kleber.acampamentoadventista.R;

public class RoteiroSextaFragment extends Fragment {


    public RoteiroSextaFragment() {
        // Required empty public constructor
    }

    TextView titulo;
    TextView conteudo;
    Bundle dicionario;

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

            titulo.setText(r.getTitle());
            conteudo.setText(r.getContent());
        }
        return view;

    }

}
