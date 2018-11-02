package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleber.acampamentoadventista.R;

public class RoteiroFragment extends Fragment {


    public RoteiroFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roteiro_fragmento_sexta, container, false);

        //Referencia os componentes
        TextView titulo = view.findViewById(R.id.titulo_sexta);
        TextView conteudo = view.findViewById(R.id.conteudo_sexta);


        // chamar isso aqui só uma vez!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        Bundle dicionario = getArguments();

        //Seta os valores
        if (dicionario.getSerializable("roteiro") != null){
            setaDicionario(dicionario, titulo, conteudo);
        }

        //----------------------------------------------------------------------

        return view;

    }

    public void setRoteiro(String titulo2) {
        TextView titulo = getActivity().findViewById(R.id.titulo_sexta);
        titulo.setText(titulo2);
//        TextView conteudo = getView().findViewById(R.id.conteudo_sexta);
//        conteudo.setText(roteiro.getConteudo());
    }


    public void setaDicionario(Bundle dicionario, TextView titulo, TextView conteudo) {

        com.example.kleber.acampamentoadventista.modelos.Roteiro r =
                (com.example.kleber.acampamentoadventista.modelos.Roteiro)
                        dicionario.getSerializable("roteiro");
        titulo.setText(r.getTitulo());
        conteudo.setText(r.getConteudo());
    }

}
