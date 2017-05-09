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
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.VendaRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.Alerta;


public class EditarClienteActivity extends AppCompatActivity {

    /*COMPONENTES DA TELA*/
    EditText editTextNome;
    EditText editTextTelefone;
    EditText editTextEmail;
    Button buttonAlterar;
    public int id_pessoa;
    public String nome_antigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);


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

        editTextEmail           = (EditText) this.findViewById(R.id.editTextEmail);

        editTextTelefone           = (EditText) this.findViewById(R.id.editTextTelefone);

        buttonAlterar           = (Button) this.findViewById(R.id.buttonAlterar);

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        this.finish();

    }

    //MÉTODO CRIA OS EVENTOS PARA OS COMPONENTES
    protected  void CriarEventos(){

        editTextEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextNome.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextTelefone.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //CRIANDO EVENTO CLICK PARA O BOTÃO ALTERAR
        buttonAlterar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Alterar_onClick();
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
        else if(editTextEmail.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.email_obrigatorio));

            editTextEmail.requestFocus();

        }
        else if(editTextTelefone.getText().toString().trim().equals("")){

            Alerta.Alert(this, this.getString(R.string.telefone_obigatorio));

            editTextTelefone.requestFocus();

        }
        else{
            String nome_atual = editTextNome.getText().toString().trim();

            if ( !(nome_atual.equals(nome_antigo)) && (new ClienteRepository(this).cliente_existe(nome_atual)))
                Alerta.Alert(this, this.getString(R.string.alerta_cliente));
            else {
                VendaRepository vendaRepository = new VendaRepository(this);
                vendaRepository.AtualizarCliente(nome_antigo, nome_atual);
            /*CRIANDO UM OBJETO PESSOA*/
                ClienteModel pessoaModel = new ClienteModel();

                pessoaModel.setCodigo(id_pessoa);

            /*SETANDO O VALOR DO CAMPO NOME*/
                pessoaModel.setNome(nome_atual);

            /*SETANDO O EMAIL*/
                pessoaModel.setEmail(editTextEmail.getText().toString().trim());

             /*SETANDO O TELEFONE*/
                pessoaModel.setTelefone(editTextTelefone.getText().toString().trim());

            /*ALTERANDO O REGISTRO*/
                new ClienteRepository(this).Atualizar(pessoaModel);

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
                        intentRedirecionar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intentRedirecionar.putExtra("SWITCH_TAB", 2);
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

        ClienteRepository pessoaRepository = new ClienteRepository(this);

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        id_pessoa = extra.getInt("id_cliente");

        //CONSULTA UMA PESSOA POR ID
        ClienteModel pessoaModel = pessoaRepository.GetPessoa(id_pessoa);
        nome_antigo = pessoaModel.getNome();
        //SETA O NOME NA VIEW
        editTextNome.setText(nome_antigo);

        //SETA O EMAIL NA VIEW
        editTextEmail.setText(pessoaModel.getEmail());

        //SETA O TELEFONE NA VIEW
        editTextTelefone.setText(pessoaModel.getTelefone());

    }
}
