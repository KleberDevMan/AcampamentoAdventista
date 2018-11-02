package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;

public abstract class RoteiroFragment extends Fragment {


    public RoteiroFragment() {
        // Required empty public constructor
    }

    protected TextView titulo;
    protected TextView conteudo;
    protected Bundle dicionario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roteiro_fragmento_sexta, container, false);

    }


}
