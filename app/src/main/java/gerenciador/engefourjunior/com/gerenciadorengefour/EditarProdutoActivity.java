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

public class EditarProdutoActivity extends AppCompatActivity {

    /*COMPONENTES DA TELA*/
    EditText         editTextCodigo;
    EditText         editTextNome;
    EditText         editTextValor;
    Button           buttonAlterar;
    Button           buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);


        //CHAMA O MÉTODO PARA CRIAR OS COMPONENTES DA TELA
        this.CriarComponentes();

        //CHAMA O MÉTODO QUE CRIA EVENTOS PARA OS COMPONENTES
        this.CriarEventos();

        //CARREGA OS VALORES NOS CAMPOS DA TELA.
        this.CarregaValoresCampos();
    }

    //VINCULA OS COMPONENTES DA TELA(VIEW) AOS OBJETOS DECLARADOS.
    protected  void CriarComponentes(){

        editTextCodigo         = (EditText) this.findViewById(R.id.editTextCodigo);

        editTextNome           = (EditText) this.findViewById(R.id.editTextNome);

        editTextValor           = (EditText) this.findViewById(R.id.editTextValor);

        buttonAlterar           = (Button) this.findViewById(R.id.buttonAlterar);

        buttonVoltar           = (Button) this.findViewById(R.id.buttonVoltar);

    }

    //MÉTODO CRIA OS EVENTOS PARA OS COMPONENTES
    protected  void CriarEventos(){


        //CRIANDO EVENTO CLICK PARA O BOTÃO ALTERAR
        buttonAlterar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Alterar_onClick();
            }
        });

        //CRIANDO EVENTO CLICK PARA O BOTÃO VOLTAR
        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });
    }

    //ALTERA UM REGISTRO
    protected  void Alterar_onClick(){

        //VALIDA SE OS CAMPOS ESTÃO VAZIOS ANTES DE ALTERAR O REGISTRO
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

            pessoaModel.setCodigo(Integer.parseInt(editTextCodigo.getText().toString()));

            /*SETANDO O VALOR DO CAMPO NOME*/
            pessoaModel.setNome(editTextNome.getText().toString().trim());

            /*SETANDO O VALOR DO CAMPO VALOR*/
            pessoaModel.setValor(Float.parseFloat(editTextCodigo.getText().toString()));

            /*ALTERANDO O REGISTRO*/
            new ProdutoRepository(this).Atualizar(pessoaModel);

            /*MENSAGEM DE SUCESSO!*/
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
            alertDialog.setTitle(R.string.app_name);

            //MENSAGEM A SER EXIBIDA
            alertDialog.setMessage("Registro alterado com sucesso! ");

            //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                    //RETORNA PARA A TELA DE CONSULTA
                    Intent intentRedirecionar = new Intent(getApplicationContext(), ConsultarProdutoActivity.class);
                    startActivity(intentRedirecionar);
                    finish();
                }
            });

            //MOSTRA A MENSAGEM NA TELA
            alertDialog.show();
        }
    }

    //CARREGA OS VALORES NOS CAMPOS APÓS RETORNAR DO SQLITE
    protected  void CarregaValoresCampos(){

        ProdutoRepository pessoaRepository = new ProdutoRepository(this);

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        int id_pessoa = extra.getInt("id_produto");

        //CONSULTA UMA PESSOA POR ID
        ProdutoModel pessoaModel = pessoaRepository.GetPessoa(id_pessoa);

        //SETA O CÓDIGO NA VIEW
        editTextCodigo.setText(String.valueOf(pessoaModel.getCodigo()));

        //SETA O NOME NA VIEW
        editTextNome.setText(pessoaModel.getNome());

        //SETA O VALOR NA VIEW
        editTextValor.setText(String.valueOf(pessoaModel.getValor()));

    }
}
