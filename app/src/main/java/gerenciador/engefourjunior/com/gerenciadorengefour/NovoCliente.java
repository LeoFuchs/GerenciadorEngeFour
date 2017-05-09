package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ClienteModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ClienteRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.Alerta;

public class NovoCliente extends AppCompatActivity {


    /*COMPONENTES DA TELA*/
    EditText editTextNome;
    EditText editTextEmail;
    EditText editTextTelefone;
    Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_cliente);


        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        this.CriarComponentes();

        //CRIA OS EVENTOS DOS COMPONENTES
        this.CriarEventos();
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        this.finish();

    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){

        editTextNome           = (EditText) this.findViewById(R.id.editTextProduto);

        editTextEmail           = (EditText) this.findViewById(R.id.editTextEmail);

        editTextTelefone         = (EditText) this.findViewById(R.id.editTextTelefone);

        buttonSalvar           = (Button) this.findViewById(R.id.buttonSalvar);

    }
    //CRIA OS EVENTOS DOS COMPONENTES
    protected  void CriarEventos(){

        editTextEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextNome.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextTelefone.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //CRIANDO EVENTO NO BOTÃO SALVAR
        buttonSalvar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Salvar_onClick();
            }
        });

    }

    //VALIDA OS CAMPOS E SALVA AS INFORMAÇÕES NO BANCO DE DADOS
    protected  void Salvar_onClick(){

        if(editTextNome.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.nome_obrigatorio));

            editTextNome.requestFocus();
        }
        else if(editTextEmail.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.email_obrigatorio));

            editTextEmail.requestFocus();

        }
        else if(editTextTelefone.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.telefone_obigatorio));

            editTextTelefone.requestFocus();
        }

        else {
            if (new ClienteRepository(this).cliente_existe(editTextNome.getText().toString().trim()))
                Alerta.Alert(this, this.getString(R.string.alerta_cliente));
            else {
            /*CRIANDO UM OBJETO PESSOA*/
                ClienteModel pessoaModel = new ClienteModel();

            /*SETANDO O VALOR DO CAMPO NOME*/
                pessoaModel.setNome(editTextNome.getText().toString().trim());

            /*SETANDO O VALOR DO CAMPO EMAIL*/
                pessoaModel.setEmail(editTextEmail.getText().toString().trim());

            /*SETANDO O VALOR DO CAMPO TELEFONE*/
                pessoaModel.setTelefone(editTextTelefone.getText().toString().trim());

            /*SALVANDO UM NOVO REGISTRO*/
                new ClienteRepository(this).Salvar(pessoaModel);

            /*MENSAGEM DE SUCESSO!*/
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
                alertDialog.setTitle(R.string.app_name);

                //MENSAGEM A SER EXIBIDA
                alertDialog.setMessage("Cliente cadastrado com sucesso! ");

                //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //RETORNA PARA A TELA DE CONSULTA
                        Intent intentRedirecionar = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentRedirecionar);
                        finish();
                    }
                });

                //MOSTRA A MENSAGEM NA TELA
                alertDialog.show();

                LimparCampos();
            }
        }

    }

    //LIMPA OS CAMPOS APÓS SALVAR AS INFORMAÇÕES
    protected void LimparCampos(){

        editTextNome.setText(null);
        editTextTelefone.setText(null);
        editTextEmail.setText(null);
    }
}
