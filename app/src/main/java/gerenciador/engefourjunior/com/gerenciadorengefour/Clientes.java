package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ClienteModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ClienteRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.LinhaConsultarClienteAdapter;

public class Clientes extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clientes_layout, container, false);

        ClienteRepository pessoaRepository =  new ClienteRepository(this.getActivity());

        //BUSCA AS PESSOAS CADASTRADAS
        List<ClienteModel> pessoas = pessoaRepository.SelecionarTodos();

        //SETA O ADAPTER DA LISTA COM OS REGISTROS RETORNADOS DA BASE
        ListView lView = (ListView) view.findViewById(R.id.listClientes);
        lView.setAdapter(new LinhaConsultarClienteAdapter(this, pessoas));
        return view;
    }
}
