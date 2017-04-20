package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ClienteModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ClienteRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.LinhaConsultarClienteAdapter;

public class ConsultarClienteActivity extends AppCompatActivity {

    //CRIANDO UM OBJETO DO TIPO ListView PARA RECEBER OS REGISTROS DE UM ADAPTER
    ListView listViewPessoas;

    //CRIANDO O BOTÃO VOLTAR PARA RETORNAR PARA A TELA COM AS OPÇÕES
    Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_cliente);

        //VINCULANDO O LISTVIEW DA TELA AO OBJETO CRIADO
        listViewPessoas = (ListView)this.findViewById(R.id.listViewPessoas);

        //VINCULANDO O BOTÃO VOLTAR DA TELA AO OBJETO CRIADO
        buttonVoltar    = (Button)this.findViewById(R.id.buttonVoltar);


        //CHAMA O MÉTODO QUE CARREGA AS PESSOAS CADASTRADAS NA BASE DE DADOS
        this.CarregarPessoasCadastradas();

        //CHAMA O MÉTODO QUE CRIA O EVENTO PARA O BOTÃO VOLTAR
        this.CriarEvento();
    }

    //MÉTODO QUE CRIA EVENTO PARA O BOTÃO VOLTAR
    protected  void CriarEvento(){

        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //REDIRECIONA PARA A TELA PRINCIPAL
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);

                //FINALIZA A ATIVIDADE ATUAL
                finish();
            }
        });
    }

    //MÉTODO QUE CONSULTA AS PESSOAS CADASTRADAS
    protected  void CarregarPessoasCadastradas(){

        ClienteRepository pessoaRepository =  new ClienteRepository(this);

        //BUSCA AS PESSOAS CADASTRADAS
        List<ClienteModel> pessoas = pessoaRepository.SelecionarTodos();

        //SETA O ADAPTER DA LISTA COM OS REGISTROS RETORNADOS DA BASE
        listViewPessoas.setAdapter(new LinhaConsultarClienteAdapter(this, pessoas));
    }

}
