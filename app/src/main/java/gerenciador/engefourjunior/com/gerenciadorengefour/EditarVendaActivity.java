package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.VendaModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ClienteRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ProdutoRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.VendaRepository;

public class EditarVendaActivity extends AppCompatActivity {


    /*COMPONENTES DA TELA*/
    Spinner spinnerCliente;
    Spinner spinnerProduto;
    EditText editTextQuantidade;
    EditText editTextCodigo;
    TextView textViewValorTotal;
    TextView textViewValorDevendo  ;
    EditText editTextValorPago;
    Button buttonAlterar;
    ProdutoRepository produtoRepository;
    public Float divida;
    public int id_pessoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_venda);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        this.CriarComponentes();

        //CRIA OS EVENTOS DOS COMPONENTES
        this.CriarEventos();

        //POPULA OS SPINNERS DA ACTIVITY
        this.popularSpinner();

        this.CarregaValoresCampos();

        this.atualizar_pagina();
    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){

        spinnerCliente         =(Spinner) this.findViewById(R.id.spinnerCliente);

        spinnerProduto         =(Spinner) this.findViewById(R.id.spinnerProduto);

        editTextQuantidade     =(EditText) this.findViewById(R.id.editTextQuantidade);

        textViewValorTotal         =(TextView) this.findViewById(R.id.textViewValor);

        editTextValorPago      =(EditText) this.findViewById(R.id.editTextValorPago);

        buttonAlterar         = (Button) this.findViewById(R.id.buttonAlterar);

        produtoRepository =  new ProdutoRepository(this);


    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        this.finish();

    }
    //CRIA OS EVENTOS DOS COMPONENTES
    protected  void CriarEventos() {
        //CRIANDO EVENTO NO BOTÃO SALVAR
        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alterar_onClick();
            }
        });

        editTextValorPago.setText("0.0");
        editTextQuantidade.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextQuantidade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    atualizar_pagina();
                    return true;
                }
                return false;
            }
        });
        editTextValorPago.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextValorPago.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    atualizar_pagina();
                    return true;
                }
                return false;
            }
        });
        spinnerProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                atualizar_pagina();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    //VALIDA OS CAMPOS E SALVA AS INFORMAÇÕES NO BANCO DE DADOS
    protected  void Alterar_onClick(){

            /*CRIANDO UM OBJETO PESSOA*/
        VendaModel pessoaModel = new VendaModel();
        if (editTextQuantidade.getText().toString().equals(""))
            editTextQuantidade.setText("1");
        if (editTextValorPago.getText().toString().equals(""))
            editTextValorPago.setText("0");
            /*SETANDO O VALOR DO CAMPO NOME*/
        pessoaModel.setQuantidade(Integer.valueOf(editTextQuantidade.getText().toString().trim()));

        pessoaModel.setDs_Nome_cliente(spinnerCliente.getSelectedItem().toString());

        pessoaModel.setDs_Nome_produto(spinnerProduto.getSelectedItem().toString());

        pessoaModel.setSaldo(divida);

        String[] aux = textViewValorTotal.getText().toString().split(" ");
        Float valor_total = Float.parseFloat(aux[1]);
        pessoaModel.setValor_total(valor_total);

        pessoaModel.setValor_pago(Float.parseFloat(editTextValorPago.getText().toString()));

        pessoaModel.setCodigo(id_pessoa);

            /*SALVANDO UM NOVO REGISTRO*/
        new VendaRepository(this).Atualizar(pessoaModel);

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

        LimparCampos();



    }

    //LIMPA OS CAMPOS APÓS SALVAR AS INFORMAÇÕES
    protected void LimparCampos(){
        editTextQuantidade.setText("1");
        textViewValorTotal.setText("R$ 0.0");
        editTextValorPago.setText("0.0");
    }

    protected void popularSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCliente);

        ClienteRepository pessoaRepository =  new ClienteRepository(this);

        //BUSCA AS PESSOAS CADASTRADAS
        List<String> pessoas = pessoaRepository.SelecionarNomes();

        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(this,
                R.layout.spinner_item, pessoas);

        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterr);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerProduto);

        //BUSCA AS PESSOAS CADASTRADAS
        List<String> produtos = produtoRepository.SelecionarNomes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, produtos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
    }
    public void atualizar_pagina(){
        int quantidade;
        Float valorpago;
        String produto = spinnerProduto.getSelectedItem().toString();
        if (editTextQuantidade.getText().toString().equals(""))
            quantidade = Integer.parseInt(String.valueOf(editTextQuantidade.getHint()));
        else
            quantidade = Integer.parseInt(String.valueOf(editTextQuantidade.getText()));
        Float preço = produtoRepository.consultarPreço(produto, quantidade);
        textViewValorTotal.setText("R$ " + preço);

        if (editTextValorPago.getText().toString().equals(""))
            valorpago = Float.parseFloat(editTextValorPago.getHint().toString());
        else
            valorpago = Float.parseFloat(editTextValorPago.getText().toString());
        divida = preço - valorpago;

        if(getCurrentFocus()!= null) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    protected  void CarregaValoresCampos(){

        VendaRepository pessoaRepository = new VendaRepository(this);

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        id_pessoa = extra.getInt("id_venda");

        //CONSULTA UMA PESSOA POR ID
        VendaModel pessoaModel = pessoaRepository.GetPessoa(id_pessoa);

        //SETA O NOME NA VIEW
        editTextQuantidade.setText(String.valueOf(pessoaModel.getQuantidade()));

        //SETA O VALOR NA VIEW
        editTextValorPago.setText(String.valueOf(pessoaModel.getValor_pago()));

        String myString = pessoaModel.getDs_nome_cliente(); //the value you want the position for
        ArrayAdapter myAdap = (ArrayAdapter) spinnerCliente.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(myString);
        spinnerCliente.setSelection(spinnerPosition);

        String myString2 = pessoaModel.getDs_nome_produto(); //the value you want the position for
        ArrayAdapter myAdap2 = (ArrayAdapter) spinnerProduto.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition2 = myAdap2.getPosition(myString2);
        spinnerProduto.setSelection(spinnerPosition2);
    }
}



