package br.com.kleberjunio.acampamentoadventista.fragmentos.informes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.kleberjunio.acampamentoadventista.R;
import br.com.kleberjunio.acampamentoadventista.activity.enuns.Informes;
import br.com.kleberjunio.acampamentoadventista.activity.enuns.Roteiros;
import br.com.kleberjunio.acampamentoadventista.modelos.informepojo.Informe;
import br.com.kleberjunio.acampamentoadventista.modelos.roteiropojo.Roteiro;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MandamentosFragment extends Fragment {


    public MandamentosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mandamentos, container, false);

        //REFERENCIO COMPONENTES
        TextView conteudo = view.findViewById(R.id.conteudo_mandamentos);
        ImageView imageView = view.findViewById(R.id.image_mandamentos);

        Bundle dicionario = getArguments();

        if (dicionario.size() != 0) {
            //PEGO OBJETO NO DICIONARIO DE DADOS
            Informe informe = (Informe) dicionario.getSerializable(Informes.MANDAMENTOS.name());

            //SETO DADOS NA VIEW
            conteudo.setText(informe.getContent().replace("\\n", "\n"));
            Picasso.get().load(informe.getUrlImage()).into(imageView);
        }

        return view;
    }

}
