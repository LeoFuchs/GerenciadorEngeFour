package gerenciador.engefourjunior.com.gerenciadorengefour.Model;

public class ProdutoModel {
    private Integer codigo;
    private String  nome;
    private Float  valor;


    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

}