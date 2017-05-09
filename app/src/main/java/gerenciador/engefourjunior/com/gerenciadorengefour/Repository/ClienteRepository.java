package gerenciador.engefourjunior.com.gerenciadorengefour.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import gerenciador.engefourjunior.com.gerenciadorengefour.Model.ClienteModel;
import gerenciador.engefourjunior.com.gerenciadorengefour.Uteis.DataBase;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ClienteRepository {

    DataBase databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public ClienteRepository(Context context){

        databaseUtil =  new DataBase(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param pessoaModel
     */
    public void Salvar(ClienteModel pessoaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_nome",        pessoaModel.getNome());
        contentValues.put("ds_email",       pessoaModel.getEmail());
        contentValues.put("ds_telefone",    pessoaModel.getTelefone());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("tb_cliente",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param pessoaModel
     */
    public void Atualizar(ClienteModel pessoaModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_nome",       pessoaModel.getNome());
        contentValues.put("ds_email",      pessoaModel.getEmail());
        contentValues.put("ds_telefone",   pessoaModel.getTelefone());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("tb_cliente", contentValues, "id_cliente = ?", new String[]{Integer.toString(pessoaModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("tb_cliente","id_cliente = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public ClienteModel GetPessoa(int codigo){


        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_cliente WHERE id_cliente= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        ClienteModel pessoaModel =  new ClienteModel();

        //ADICIONANDO OS DADOS DA PESSOA
        pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_cliente")));
        pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        pessoaModel.setEmail(cursor.getString(cursor.getColumnIndex("ds_email")));
        pessoaModel.setTelefone(cursor.getString(cursor.getColumnIndex("ds_telefone")));

        //RETORNANDO A PESSOA
        return pessoaModel;
    }

    public boolean cliente_existe(String nome_cliente){

        boolean cliente_existe = TRUE;
        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_cliente WHERE ds_nome= '"+ nome_cliente+"'",null);
        cursor.moveToFirst();
        if(cursor.isAfterLast())
            cliente_existe = FALSE;

        return cliente_existe;
    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<ClienteModel> SelecionarTodos(){

        List<ClienteModel> pessoas = new ArrayList<ClienteModel>();

        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_cliente,      ");
        stringBuilderQuery.append("        ds_nome,        ");
        stringBuilderQuery.append("        ds_email,    ");
        stringBuilderQuery.append("        ds_telefone        ");
        stringBuilderQuery.append("  FROM  tb_cliente       ");
        stringBuilderQuery.append(" ORDER BY ds_nome       ");

        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ClienteModel pessoaModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            pessoaModel =  new ClienteModel();

            //ADICIONANDO OS DADOS DA PESSOA
            pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_cliente")));
            pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
            pessoaModel.setEmail(cursor.getString(cursor.getColumnIndex("ds_email")));
            pessoaModel.setTelefone(cursor.getString(cursor.getColumnIndex("ds_telefone")));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(pessoaModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;

    }

    public List<String> SelecionarNomes(){

        List<String> pessoas = new ArrayList<String>();

        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT ds_nome      ");
        stringBuilderQuery.append("  FROM  tb_cliente       ");

        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        String NomePessoa;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            NomePessoa =  new String();

            //ADICIONANDO OS DADOS DA PESSOA
            NomePessoa=cursor.getString(cursor.getColumnIndex("ds_nome"));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(NomePessoa);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;
    }

}

