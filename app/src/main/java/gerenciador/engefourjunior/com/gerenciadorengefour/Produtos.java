package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ProdutoModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Repository.ProdutoRepository;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.LinhaConsultarProdutoAdapter;

public class Produtos extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.produtos_layout, container, false);

        ProdutoRepository pessoaRepository =  new ProdutoRepository(this.getActivity());

        //BUSCA AS PESSOAS CADASTRADAS
        List<ProdutoModel> pessoas = pessoaRepository.SelecionarTodos();

        //SETA O ADAPTER DA LISTA COM OS REGISTROS RETORNADOS DA BASE
        ListView lView = (ListView) view.findViewById(R.id.listProdutos);
        lView.setAdapter(new LinhaConsultarProdutoAdapter(this, pessoas));
        return view;
    }
}
