package br.com.kleberjunio.acampamentoadventista.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    //realiza validacoes
    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode) {

        //verifica se user esta usando uma versao acima Marchimelow
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<>();

            /*
            * percorre a lista de permissoes passada,
            * verificando uma a uma
            * se já tem permissao liberada
            * */
            for (String permissao : permissoes) {
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao)
                        == PackageManager.PERMISSION_GRANTED;
                if ( !temPermissao ) {
                    listaPermissoes.add(permissao);
                }
            }

            if ( listaPermissoes.isEmpty() ) {
                return true;
            }

            String[] novasPermissoes = new String[ listaPermissoes.size() ];
            listaPermissoes.toArray( novasPermissoes );

            //solicita permissao
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }

        return true;
    }

}
