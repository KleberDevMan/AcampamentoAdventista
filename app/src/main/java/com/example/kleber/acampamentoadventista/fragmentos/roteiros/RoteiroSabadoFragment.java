package com.example.kleber.acampamentoadventista.fragmentos.roteiros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.modelos.Roteiro;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoteiroSabadoFragment extends Fragment {


    public RoteiroSabadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roteiro_fragmento_sabado, container, false);
    }


}
