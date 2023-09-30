package model;

import java.time.LocalTime;
import java.util.Date;


public class Procedimento {
    private int id;
    private String nome;
    private Consulta consulta;
    private Date dia;
    private LocalTime horario;
    private String estado;
    private double valor;
    private String laudo;
    private Date dataCriacao;
    private Date dataModificacao;

    public Procedimento(String nome, Consulta consulta, Date dia, LocalTime horario, String estado, double valor, String laudo, Date dataCriacao, Date dataModificacao) {
        this.nome = nome;
        this.consulta = consulta;
        this.dia = dia;
        this.horario = horario;
        this.estado = estado;
        this.valor = valor;
        this.laudo = laudo;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "Procedimento{" + "id=" + id + ", nome=" + nome + ", consulta=" + consulta + ", dia=" + dia + ", horario=" + horario + ", estado=" + estado + ", valor=" + valor + ", laudo=" + laudo + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getLaudo() {
        return laudo;
    }

    public void setLaudo(String laudo) {
        this.laudo = laudo;
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
