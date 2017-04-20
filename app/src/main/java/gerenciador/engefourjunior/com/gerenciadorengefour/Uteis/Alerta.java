package gerenciador.engefourjunior.com.gerenciadorengefour.Uteis;


import android.app.AlertDialog;
import android.content.Context;

import gerenciador.engefourjunior.com.gerenciadorengefour.R;

public class Alerta {
    public static void Alert(Context context, String mensagem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(R.string.app_name);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);

        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }
}
