package gerenciador.engefourjunior.com.gerenciadorengefour.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.VendaModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.DataBase;

public class VendaRepository {

    DataBase databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public VendaRepository(Context context) {

        databaseUtil = new DataBase(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param pessoaModel
     */
    public void Salvar(VendaModel pessoaModel) {

        ContentValues contentValues = new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_nome_cliente", pessoaModel.getDs_nome_cliente());
        contentValues.put("ds_nome_produto", pessoaModel.getDs_nome_produto());
        contentValues.put("ds_quantidade", pessoaModel.getQuantidade());
        contentValues.put("ds_valor_total", pessoaModel.getValor_total());
        contentValues.put("ds_valor_pago", pessoaModel.getValor_pago());
        contentValues.put("ds_saldo", pessoaModel.getSaldo());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("tb_venda", null, contentValues);
    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param pessoaModel
     */
    public void Atualizar(VendaModel pessoaModel) {

        ContentValues contentValues = new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_nome_cliente", pessoaModel.getDs_nome_cliente());
        contentValues.put("ds_nome_produto", pessoaModel.getDs_nome_produto());
        contentValues.put("ds_quantidade", pessoaModel.getQuantidade());
        contentValues.put("ds_valor_total", pessoaModel.getValor_total());
        contentValues.put("ds_valor_pago", pessoaModel.getValor_pago());
        contentValues.put("ds_saldo", pessoaModel.getSaldo());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("tb_venda", contentValues, "id_venda = ?", new String[]{Integer.toString(pessoaModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo) {

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("tb_venda", "id_venda = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public VendaModel GetPessoa(int codigo) {


        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_venda WHERE id_venda= " + codigo, null);
        Log.d("DB", DatabaseUtils.dumpCursorToString(cursor));
        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        VendaModel pessoaModel = new VendaModel();
        //ADICIONANDO OS DADOS DA PESSOA
        pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_venda")));
        pessoaModel.setDs_Nome_cliente(cursor.getString(cursor.getColumnIndex("ds_nome_cliente")));
        pessoaModel.setDs_Nome_produto(cursor.getString(cursor.getColumnIndex("ds_nome_produto")));
        pessoaModel.setQuantidade(cursor.getInt(cursor.getColumnIndex("ds_quantidade")));
        pessoaModel.setValor_total(cursor.getFloat(cursor.getColumnIndex("ds_valor_total")));
        pessoaModel.setValor_pago(cursor.getFloat(cursor.getColumnIndex("ds_valor_pago")));
        pessoaModel.setSaldo(cursor.getFloat(cursor.getColumnIndex("ds_saldo")));

        //RETORNANDO A PESSOA
        return pessoaModel;
    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<VendaModel> SelecionarTodos() {

        List<VendaModel> pessoas = new ArrayList<VendaModel>();

        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_venda,       ");
        stringBuilderQuery.append("        ds_quantidade,  ");
        stringBuilderQuery.append("        ds_valor_total, ");
        stringBuilderQuery.append("        ds_valor_pago, ");
        stringBuilderQuery.append("        ds_saldo,        ");
        stringBuilderQuery.append("        ds_nome_cliente,     ");
        stringBuilderQuery.append("        ds_nome_produto    ");
        stringBuilderQuery.append("  FROM  tb_venda        ");
        stringBuilderQuery.append(" ORDER BY id_venda  DESC    ");

        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        VendaModel pessoaModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()) {

            /* CRIANDO UMA NOVA PESSOAS */
            pessoaModel = new VendaModel();

            //ADICIONANDO OS DADOS DA PESSOA
            pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_venda")));
            pessoaModel.setDs_Nome_produto(cursor.getString(cursor.getColumnIndex("ds_nome_produto")));
            pessoaModel.setDs_Nome_cliente(cursor.getString(cursor.getColumnIndex("ds_nome_cliente")));
            pessoaModel.setQuantidade(cursor.getInt(cursor.getColumnIndex("ds_quantidade")));
            pessoaModel.setValor_total(cursor.getFloat(cursor.getColumnIndex("ds_valor_total")));
            pessoaModel.setValor_pago(cursor.getFloat(cursor.getColumnIndex("ds_valor_pago")));
            pessoaModel.setSaldo(cursor.getFloat(cursor.getColumnIndex("ds_saldo")));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(pessoaModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;
}
    public void AtualizarProduto(String nome_antigo, String nome_atual){

        List<VendaModel> pessoas = new ArrayList<VendaModel>();

        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT * ");
        stringBuilderQuery.append("  FROM  tb_venda        ");
        stringBuilderQuery.append("  WHERE  ds_nome_produto = '"+ nome_antigo+"'");
        stringBuilderQuery.append(" ORDER BY id_venda  DESC    ");

        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();

        VendaModel pessoaModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){
            /* CRIANDO UMA NOVA PESSOAS */
            ContentValues contentValues = new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
            contentValues.put("ds_nome_cliente", cursor.getString(cursor.getColumnIndex("ds_nome_cliente")));
            contentValues.put("ds_nome_produto", nome_atual);
            contentValues.put("ds_quantidade", cursor.getInt(cursor.getColumnIndex("ds_quantidade")));
            contentValues.put("ds_valor_total", cursor.getFloat(cursor.getColumnIndex("ds_valor_total")));
            contentValues.put("ds_valor_pago", cursor.getFloat(cursor.getColumnIndex("ds_valor_pago")));
            contentValues.put("ds_saldo", cursor.getFloat(cursor.getColumnIndex("ds_saldo")));

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
            databaseUtil.GetConexaoDataBase().update("tb_venda", contentValues, "id_venda = ?", new String[]{Integer.toString(cursor.getInt(cursor.getColumnIndex("id_venda")))});

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }
        //RETORNANDO A LISTA DE PESSOAS
    }

    public void AtualizarCliente(String nome_antigo, String nome_atual){

        List<VendaModel> pessoas = new ArrayList<VendaModel>();

        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT * ");
        stringBuilderQuery.append("  FROM  tb_venda        ");
        stringBuilderQuery.append("  WHERE  ds_nome_cliente = '"+ nome_antigo+"'");
        stringBuilderQuery.append(" ORDER BY id_venda  DESC    ");

        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){
            /* CRIANDO UMA NOVA PESSOAS */
            ContentValues contentValues = new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
            contentValues.put("ds_nome_cliente",nome_atual);
            contentValues.put("ds_nome_produto",cursor.getString(cursor.getColumnIndex("ds_nome_produto")));
            contentValues.put("ds_quantidade", cursor.getInt(cursor.getColumnIndex("ds_quantidade")));
            contentValues.put("ds_valor_total", cursor.getFloat(cursor.getColumnIndex("ds_valor_total")));
            contentValues.put("ds_valor_pago", cursor.getFloat(cursor.getColumnIndex("ds_valor_pago")));
            contentValues.put("ds_saldo", cursor.getFloat(cursor.getColumnIndex("ds_saldo")));

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
            databaseUtil.GetConexaoDataBase().update("tb_venda", contentValues, "id_venda = ?", new String[]{Integer.toString(cursor.getInt(cursor.getColumnIndex("id_venda")))});

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }
        //RETORNANDO A LISTA DE PESSOAS
    }
}

