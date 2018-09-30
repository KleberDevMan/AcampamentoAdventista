package com.example.kleber.acampamentoadventista.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.kleber.acampamentoadventista.R;
import com.example.kleber.acampamentoadventista.helper.Permissao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class CompartilharActivity extends AppCompatActivity {

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private CardView cardCamera;
    private CardView cardGaleria;

    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartilhar);

        configuraActionBar();

        //Validar permissoes
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        inicializaComponentes();

        //Trantando evento card_camera
//        cardCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //abrir camera
//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                //verifica se é possivel executar essa intent
//                if (i.resolveActivity(getPackageManager()) != null) {
//                    //inializa a activity e captura o valor de retorno
//                    startActivityForResult(i, SELECAO_CAMERA);
//                }
//            }
//        });

        //Trantando evento card_galeria

    }

    public void btnTirarFoto(View view) {
        //abrir camera
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //verifica se é possivel executar essa intent
        if (i.resolveActivity(getPackageManager()) != null) {
            //inializa a activity e captura o valor de retorno
            startActivityForResult(i, SELECAO_CAMERA);
        }
    }

    public void btnEntrarGaleria(View view) {
        //abrir galeria em um determinado local
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //verifica se é possivel executar essa intent
        if (i.resolveActivity(getPackageManager()) != null) {
            //inializa a activity e captura o valor de retorno
            startActivityForResult(i, SELECAO_GALERIA);
        }
    }

    //METODO QUE TRATA O RESULTADO DA CAMERA OU GALERIA E ENVIA PARA ACTIVITY MOLDURA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //dados recuperdados com sucesso
        if ( resultCode == RESULT_OK ) {
            Bitmap imagem = null;

            try {

                switch ( requestCode ) {
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localDaImagem = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localDaImagem);
                        break;
                }

                if (imagem != null){

                    //converter a imagem em byte[]
                    ByteArrayOutputStream is = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, is);
                    byte[] dadosImagem = is.toByteArray();

                    //Envia imagem para outra activity
                    Intent i = new Intent(this, MolduraActivity.class);
                    i.putExtra("foto", dadosImagem);
                    startActivity(i);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void inicializaComponentes() {
        cardCamera = findViewById(R.id.card_camera);
        cardCamera = findViewById(R.id.card_galeria);
    }

    //PERSONALIZA A ACTION_BAR
    private void configuraActionBar() {
        //configura toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            //seta de voltar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //verifica as permissoes que foram consedidas pelo usuario
        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        //cria um dialog personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para compartilhar fotos é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        //exibe dialogo
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
