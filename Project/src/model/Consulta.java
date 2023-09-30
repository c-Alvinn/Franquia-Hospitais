package model;

import java.time.LocalTime;
import java.util.Date;


public class Consulta {
    private int id;
    private Date dia;
    private LocalTime horario;
    private String estado;
    private Medico medico;
    private Pessoa paciente;
    private double valor;
    private UnidadeF unidade;
    private Date dataCriacao;
    private Date dataModificacao;

    public Consulta(Date dia, LocalTime horario, String estado, Medico medico, Pessoa paciente, double valor, UnidadeF unidade, Date dataCriacao, Date dataModificacao) {
        this.dia = dia;
        this.horario = horario;
        this.estado = estado;
        this.medico = medico;
        this.paciente = paciente;
        this.valor = valor;
        this.unidade = unidade;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    
    @Override
    public String toString() {
        return "Consulta{" + "id=" + id + ", dia=" + dia + ", horario=" + horario + ", estado=" + estado + ", medico=" + medico + ", paciente=" + paciente + ", valor=" + valor + ", unidade=" + unidade + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
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
