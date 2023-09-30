package model;

import java.util.Date;

public class FinanceiroMed {
    private int id;
    private double valorMedico;
    private Medico medico;
    private String estado;                  
    private Franquia franquia;
    private Date dataCriacao;
    private Date dataModificacao;

    public FinanceiroMed(double valorMedico, Medico medico, String estado, Franquia franquia, Date dataCriacao, Date dataModificacao) {
        this.valorMedico = valorMedico;
        this.medico = medico;
        this.estado = estado;
        this.franquia = franquia;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "FinanceiroMed{" + "id=" + id + ", valorMedico=" + valorMedico + ", medico=" + medico + ", estado=" + estado + ", franquia=" + franquia + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorMedico() {
        return valorMedico;
    }

    public void setValorMedico(double valorMedico) {
        this.valorMedico = valorMedico;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Franquia getFranquia() {
        return franquia;
    }

    public void setFranquia(Franquia franquia) {
        this.franquia = franquia;
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
