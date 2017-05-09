package gerenciador.engefourjunior.com.gerenciadorengefour.Model;

public class VendaModel {
    private Integer codigo;
    private Integer quantidade;
    private String ds_nome_cliente;
    private String ds_nome_produto;
    private Float saldo;
    private Float valor_total;
    private Float valor_pago;


    public Integer getCodigo() {
        return codigo;
    }
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDs_nome_cliente() {
        return ds_nome_cliente;
    }
    public void setDs_Nome_cliente(String ds_nome_cliente) {this.ds_nome_cliente = ds_nome_cliente;}

    public String getDs_nome_produto() {
        return ds_nome_produto;
    }
    public void setDs_Nome_produto(String ds_nome_produto) {this.ds_nome_produto = ds_nome_produto;}

    public Float getSaldo() {
        return saldo;
    }
    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public Float getValor_total() {
        return valor_total;
    }
    public void setValor_total(Float valor_total) {
        this.valor_total = valor_total;
    }

    public Float getValor_pago() {
        return valor_pago;
    }
    public void setValor_pago(Float valor_pago) {
        this.valor_pago = valor_pago;
    }
}

