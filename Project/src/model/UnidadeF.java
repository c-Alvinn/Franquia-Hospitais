package model;

import java.util.Date;



public class UnidadeF {
    private int id;
    private Franquia franquia;
    private String cidade;
    private String endereco;
    private Pessoa responsavel;
    private Date dataCriacao;
    private Date dataModificacao;

    public UnidadeF(Franquia franquia, String cidade, String endereco, Pessoa responsavel, Date dataCriacao, Date dataModificacao) {
        this.franquia = franquia;
        this.cidade = cidade;
        this.endereco = endereco;
        this.responsavel = responsavel;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "UnidadeF{" + "id=" + id + ", franquia=" + franquia + ", cidade=" + cidade + ", endereco=" + endereco + ", responsavel=" + responsavel + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Franquia getFranquia() {
        return franquia;
    }

    public void setFranquia(Franquia franquia) {
        this.franquia = franquia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
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
