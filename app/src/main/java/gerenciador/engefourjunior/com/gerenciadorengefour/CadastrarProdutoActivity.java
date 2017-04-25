package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ProdutoModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ProdutoRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.Alerta;

public class CadastrarProdutoActivity  extends AppCompatActivity {

    /*COMPONENTES DA TELA*/
    EditText        editTextNome;
    EditText        editTextValor;
    Button          buttonSalvar;
    Button          buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);


        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        this.CriarComponentes();

        //CRIA OS EVENTOS DOS COMPONENTES
        this.CriarEventos();
    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){

        editTextNome           = (EditText) this.findViewById(R.id.editTextProduto);

        editTextValor           = (EditText) this.findViewById(R.id.editTextCliente);

        buttonSalvar           = (Button) this.findViewById(R.id.buttonSalvar);

        buttonVoltar           = (Button) this.findViewById(R.id.buttonVoltar);

    }
    //CRIA OS EVENTOS DOS COMPONENTES
    protected  void CriarEventos(){

        //CRIANDO EVENTO NO BOTÃO SALVAR
        buttonSalvar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Salvar_onClick();
            }
        });

        //CRIANDO EVENTO NO BOTÃO VOLTAR
        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });
    }

    //VALIDA OS CAMPOS E SALVA AS INFORMAÇÕES NO BANCO DE DADOS
    protected  void Salvar_onClick(){

        if(editTextNome.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.nome_obrigatorio));

            editTextNome.requestFocus();
        }
        else if(editTextValor.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.valor_obrigatorio));

            editTextValor.requestFocus();

        }
        else{

            /*CRIANDO UM OBJETO PESSOA*/
            ProdutoModel pessoaModel = new ProdutoModel();

            /*SETANDO O VALOR DO CAMPO NOME*/
            pessoaModel.setNome(editTextNome.getText().toString().trim());

            /*SETANDO O VALOR DO CAMPO VALOR*/
            pessoaModel.setValor(Float.valueOf(editTextValor.getText().toString().trim()));

            /*SALVANDO UM NOVO REGISTRO*/
            new ProdutoRepository(this).Salvar(pessoaModel);

            /*MENSAGEM DE SUCESSO!*/
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
            alertDialog.setTitle(R.string.app_name);

            //MENSAGEM A SER EXIBIDA
            alertDialog.setMessage("Produto cadastrado com sucesso! ");

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

    //LIMPA OS CAMPOS APÓS SALVAR AS INFORMAÇÕES
    protected void LimparCampos(){

        editTextNome.setText(null);
        editTextValor.setText(null);
    }
}
