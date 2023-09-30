package model;

import java.util.Date;


public class FinanceiroAdm {
    private int id;
    private String tipoMovimento;
    private double valor;
    private UnidadeF unidade;
    private String descritivo;
    private Date dataCriacao;
    private Date dataModificacao;

    public FinanceiroAdm(String tipoMovimento, double valor, UnidadeF unidade, String descritivo, Date dataCriacao, Date dataModificacao) {
        this.tipoMovimento = tipoMovimento;
        this.valor = valor;
        this.unidade = unidade;
        this.descritivo = descritivo;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "FinanceiroAdm{" + "id=" + id + ", tipoMovimento=" + tipoMovimento + ", valor=" + valor + ", unidade=" + unidade + ", descritivo=" + descritivo + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public UnidadeF getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeF unidade) {
        this.unidade = unidade;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
    
}
