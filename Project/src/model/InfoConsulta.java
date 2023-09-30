package model;

import java.util.Date;



public class InfoConsulta {
    private int id;
    private Consulta consulta;
    private String descricao;
    private Date dataCriacao;
    private Date dataModificacao;

    public InfoConsulta(Consulta consulta, String descricao, Date dataCriacao, Date dataModificacao) {
        this.consulta = consulta;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "InfoConsulta{" + "id=" + id + ", consulta=" + consulta + ", descricao=" + descricao + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
