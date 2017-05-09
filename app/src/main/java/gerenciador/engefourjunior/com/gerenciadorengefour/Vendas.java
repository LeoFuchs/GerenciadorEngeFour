package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.VendaModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.VendaRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.LinhaConsultarVendaAdapter;

public class Vendas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultar_venda_layout, container, false);

        VendaRepository VendaRepository =  new VendaRepository(this.getActivity());
        //BUSCA AS PESSOAS CADASTRADAS
        List<VendaModel> vendas = VendaRepository.SelecionarTodos();
        ListView lView = (ListView) view.findViewById(R.id.listHistorico);
        //SETA O ADAPTER DA LISTA COM OS REGISTROS RETORNADOS DA BASE
        lView.setAdapter(new LinhaConsultarVendaAdapter(this, vendas));

    return view;
    }
}
