package gerenciador.engefourjunior.com.gerenciadorengefour.Uteis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.EditarVendaActivity;
import gerenciador.engefourjunior.com.gerenciadorengefour.Model.VendaModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.R;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.VendaRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Vendas;

public class LinhaConsultarVendaAdapter extends BaseAdapter {

    //CRIANDO UM OBJETO LayoutInflater PARA FAZER LINK A NOSSA VIEW(activity_linha_consultar.xml)
    private static LayoutInflater layoutInflater = null;

    //CRIANDO UMA LISTA DE PESSOAS
    List<VendaModel> vendaModels =  new ArrayList<VendaModel>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    VendaRepository vendaRepository;

    //CRIANDO UM OBJETO DA NOSSA ATIVIDADE QUE CONTEM A LISTA
    private FragmentActivity consultarActivity;

    //CONSTRUTOR QUE VAI RECEBER A NOSSA ATIVIDADE COMO PARAMETRO E A LISTA DE PESSOAS QUE VAI RETORNAR
    //DA NOSSA BASE DE DADOS
    public LinhaConsultarVendaAdapter(Vendas consultarActivity, List<VendaModel> vendaModels ) {
        this.vendaModels       =  vendaModels;
        this.consultarActivity  =  consultarActivity.getActivity();
        this.layoutInflater     = (LayoutInflater) this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vendaRepository   = new VendaRepository(consultarActivity.getActivity());
    }

    //RETORNA A QUANTIDADE DE REGISTROS DA LISTA
    @Override
    public int getCount(){
        return vendaModels.size();
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
        final View viewLinhaLista = layoutInflater.inflate(R.layout.cell_vendas,null);

        //VINCULANDO OS CAMPOS DO ARQUIVO DE LAYOUT(activity_linha_consultar.xml) AOS OBJETOS DECLARADOS.

        //CAMPO QUE VAI MOSTRAR O NOME DA PESSOA
        TextView textViewNomeCliente            = (TextView) viewLinhaLista.findViewById(R.id.textViewNomeCliente);

        //CAMPO QUE VAI MOSTRAR O ENDEREÇO DA PESSOA
        TextView textViewProduto        = (TextView) viewLinhaLista.findViewById(R.id.textViewProduto);

        //CAMPOS QUE VAI MOSTRAR O TELEFONE DA PESSOA
        TextView textViewValorTotal          = (TextView) viewLinhaLista.findViewById(R.id.textViewValor);
        TextView textViewDivida         = (TextView) viewLinhaLista.findViewById(R.id.textViewDivida);

        //CRIANDO O BOTÃO  EXCLUIR PARA DELETARMOS UM REGISTRO DO BANCO DE DADOS
        Button buttonExcluir             = (Button)   viewLinhaLista.findViewById(R.id.buttonExcluir);

        //CRIANDO O BOTÃO PARA EDITAR UM REGISTRO CADASTRADO
        Button buttonEditar            = (Button)   viewLinhaLista.findViewById(R.id.buttonEditar);

        //SETANDO O NOME NO CAMPO DA NOSSA VIEW
        textViewNomeCliente.setText(vendaModels.get(position).getDs_nome_cliente());
        textViewProduto.setText(vendaModels.get(position).getDs_nome_produto()+" x "+ String.valueOf(vendaModels.get(position).getQuantidade()));
        textViewValorTotal.setText("R$ "+ String.valueOf(vendaModels.get(position).getValor_total()));
        if(vendaModels.get(position).getSaldo()<= 0.0){
            textViewDivida.setText("OK");
            textViewDivida.setTextColor(Color.parseColor("#85bb65"));
        }
        else {
            textViewDivida.setText("- R$ " + String.valueOf(vendaModels.get(position).getSaldo()));
            textViewDivida.setTextColor(Color.parseColor("#b33a3a"));
        }
        //CRIANDO EVENTO CLICK PARA O BOTÃO DE EXCLUIR REGISTRO
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(consultarActivity)
                        .setTitle("Excluir venda")
                        .setMessage("Você tem certeza que quer excluir esta venda?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //EXCLUINDO UM REGISTRO
                                vendaRepository.Excluir(vendaModels.get(position).getCodigo());

                                //MOSTRA A MENSAGEM APÓS EXCLUIR UM REGISTRO
                                Toast.makeText(consultarActivity, "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();

                                //CHAMA O MÉTODO QUE ATUALIZA A LISTA COM OS REGISTROS QUE AINDA ESTÃO NA BASE
                                AtualizarLista();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        //CRIANDO EVENTO CLICK PARA O BOTÃO QUE VAI REDIRECIONAR PARA A TELA DE EDIÇÃO
        // DO REGISTRO.
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentRedirecionar = new Intent(consultarActivity, EditarVendaActivity.class);

                intentRedirecionar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intentRedirecionar.putExtra("id_venda",vendaModels.get(position).getCodigo());

                consultarActivity.startActivity(intentRedirecionar);

                consultarActivity.finish();
            }
        });


        return viewLinhaLista;
    }


    //ATUALIZA A LISTTA DEPOIS DE EXCLUIR UM REGISTRO
    public void AtualizarLista(){

        this.vendaModels.clear();
        this.vendaModels = vendaRepository.SelecionarTodos();
        this.notifyDataSetChanged();
    }

}
