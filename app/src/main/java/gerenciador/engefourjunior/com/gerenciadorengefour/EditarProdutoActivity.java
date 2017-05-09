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

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ProdutoModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ProdutoRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.VendaRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.Alerta;


public class EditarProdutoActivity extends AppCompatActivity {

    /*COMPONENTES DA TELA*/
    EditText editTextNome;
    EditText editTextValor;
    Button buttonAlterar;
    public int id_pessoa;
    public String nome_antigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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


        editTextNome           = (EditText) this.findViewById(R.id.editTextProduto);

        editTextValor           = (EditText) this.findViewById(R.id.editTextCliente);

        buttonAlterar           = (Button) this.findViewById(R.id.buttonAlterar);

    }

    //MÉTODO CRIA OS EVENTOS PARA OS COMPONENTES
    protected  void CriarEventos(){

        editTextNome.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextValor.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //CRIANDO EVENTO CLICK PARA O BOTÃO ALTERAR
        buttonAlterar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Alterar_onClick();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        this.finish();

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
            String nome_atual = editTextNome.getText().toString().trim();
            if ( !(nome_atual.equals(nome_antigo)) && new ProdutoRepository(this).produto_existe(nome_atual))
                Alerta.Alert(this, this.getString(R.string.alerta_produto));
            else {
                VendaRepository vendaRepository = new VendaRepository(this);
                vendaRepository.AtualizarProduto(nome_antigo, nome_atual);
            /*CRIANDO UM OBJETO PESSOA*/
                ProdutoModel pessoaModel = new ProdutoModel();

                pessoaModel.setCodigo(id_pessoa);
            /*SETANDO O VALOR DO CAMPO NOME*/
                pessoaModel.setNome(nome_atual);

            /*SETANDO O VALOR DO CAMPO VALOR*/
                pessoaModel.setValor(Float.parseFloat(editTextValor.getText().toString()));

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
                        Intent intentRedirecionar = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentRedirecionar);
                        finish();
                    }
                });

                //MOSTRA A MENSAGEM NA TELA
                alertDialog.show();
            }
        }
    }

    //CARREGA OS VALORES NOS CAMPOS APÓS RETORNAR DO SQLITE
    protected  void CarregaValoresCampos(){

        ProdutoRepository pessoaRepository = new ProdutoRepository(this);

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        id_pessoa = extra.getInt("id_produto");

        //CONSULTA UMA PESSOA POR ID
        ProdutoModel pessoaModel = pessoaRepository.GetPessoa(id_pessoa);

        //SETA O NOME NA VIEW
        editTextNome.setText(pessoaModel.getNome());
        nome_antigo = pessoaModel.getNome();
        //SETA O VALOR NA VIEW
        editTextValor.setText(String.valueOf(pessoaModel.getValor()));

    }
}
