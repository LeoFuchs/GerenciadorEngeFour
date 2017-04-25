package gerenciador.engefourjunior.com.gerenciadorengefour.Uteis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.ConsultarProdutoActivity;
import gerenciador.engefourjunior.com.gerenciadorengefour.EditarProdutoActivity;
import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ProdutoModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.R;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ProdutoRepository;

public class LinhaConsultarProdutoAdapter extends BaseAdapter {

    //CRIANDO UM OBJETO LayoutInflater PARA FAZER LINK A NOSSA VIEW(activity_linha_consultar.xml)
    private static LayoutInflater layoutInflater = null;

    //CRIANDO UMA LISTA DE PESSOAS
    List<ProdutoModel> pessoaModels =  new ArrayList<ProdutoModel>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    ProdutoRepository pessoaRepository;

    //CRIANDO UM OBJETO DA NOSSA ATIVIDADE QUE CONTEM A LISTA
    private ConsultarProdutoActivity consultarActivity;

    //CONSTRUTOR QUE VAI RECEBER A NOSSA ATIVIDADE COMO PARAMETRO E A LISTA DE PESSOAS QUE VAI RETORNAR
    //DA NOSSA BASE DE DADOS
    public LinhaConsultarProdutoAdapter(ConsultarProdutoActivity consultarActivity, List<ProdutoModel> pessoaModels ) {

        this.pessoaModels       =  pessoaModels;
        this.consultarActivity  =  consultarActivity;
        this.layoutInflater     = (LayoutInflater) this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pessoaRepository   = new ProdutoRepository(consultarActivity);
    }

    //RETORNA A QUANTIDADE DE REGISTROS DA LISTA
    @Override
    public int getCount(){

        return pessoaModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //ESSE MÉTODO SETA OS VALORES DE UM ITEM DA NOSSA LISTA DE PESSOAS PARA UMA LINHA DO NOSSO LISVIEW
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        //CRIANDO UM OBJETO DO TIPO View PARA ACESSAR O NOSSO ARQUIVO DE LAYOUT activity_linha_consultar.xml
        final View viewLinhaLista = layoutInflater.inflate(R.layout.activity_linha_consultar_produto,null);

        //VINCULANDO OS CAMPOS DO ARQUIVO DE LAYOUT(activity_linha_consultar.xml) AOS OBJETOS DECLARADOS.

        //CAMPO QUE VAI MOSTRAR O NOME DO PRODUTO
        TextView textViewNome            = (TextView) viewLinhaLista.findViewById(R.id.textViewProduto);

        //CAMPO QUE VAI MOSTRAR O VALOR DO PRODUTO
        TextView textViewValor        = (TextView) viewLinhaLista.findViewById(R.id.textViewCliente);

        //CRIANDO O BOTÃO  EXCLUIR PARA DELETARMOS UM REGISTRO DO BANCO DE DADOS
        Button buttonExcluir             = (Button)   viewLinhaLista.findViewById(R.id.buttonExcluir);

        //CRIANDO O BOTÃO PARA EDITAR UM REGISTRO CADASTRADO
        Button   buttonEditar            = (Button)   viewLinhaLista.findViewById(R.id.buttonEditar);

        //SETANDO O NOME NO CAMPO DA NOSSA VIEW
        textViewNome.setText(pessoaModels.get(position).getNome());

        //SETANDO O VALOR NO CAMPO DA NOSSA VIEW
        textViewValor.setText(pessoaModels.get(position).getValor() + " Reais");

        //CRIANDO EVENTO CLICK PARA O BOTÃO DE EXCLUIR REGISTRO
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //EXCLUINDO UM REGISTRO
                pessoaRepository.Excluir(pessoaModels.get(position).getCodigo());

                //MOSTRA A MENSAGEM APÓS EXCLUIR UM REGISTRO
                Toast.makeText(consultarActivity, "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();

                //CHAMA O MÉTODO QUE ATUALIZA A LISTA COM OS REGISTROS QUE AINDA ESTÃO NA BASE
                AtualizarLista();

            }
        });

        //CRIANDO EVENTO CLICK PARA O BOTÃO QUE VAI REDIRECIONAR PARA A TELA DE EDIÇÃO
        // DO REGISTRO.
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentRedirecionar = new Intent(consultarActivity, EditarProdutoActivity.class);

                intentRedirecionar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intentRedirecionar.putExtra("id_produto",pessoaModels.get(position).getCodigo());

                consultarActivity.startActivity(intentRedirecionar);

                consultarActivity.finish();
            }
        });

        return viewLinhaLista;
    }

    //ATUALIZA A LISTTA DEPOIS DE EXCLUIR UM REGISTRO
    public void AtualizarLista(){

        this.pessoaModels.clear();
        this.pessoaModels = pessoaRepository.SelecionarTodos();
        this.notifyDataSetChanged();
    }

}