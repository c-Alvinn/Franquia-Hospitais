package model;

import java.time.LocalDateTime;
import java.util.Date;


public class Medico {
    private int id;
    private String crm;
    private Pessoa pessoa;
    private String especialidade;
    private Date dataCriacao;
    private Date dataModificacao;

    public Medico(String crm, Pessoa pessoa, String especialidade, Date dataCriacao, Date dataModificacao) {
        this.crm = crm;
        this.pessoa = pessoa;
        this.especialidade = especialidade;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "Medico{" + "id=" + id + ", crm=" + crm + ", pessoa=" + pessoa + ", especialidade=" + especialidade + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
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
