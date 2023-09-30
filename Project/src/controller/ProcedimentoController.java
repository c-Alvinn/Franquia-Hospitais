package controller;

import model.*;

public class ProcedimentoController {
    
    private ProcedimentoDAO dao = new ProcedimentoDAO();
    
    public void mostrarProcedimentoPaciente(Pessoa pessoa){
        this.dao.mostrarProcedimentoPaciente(pessoa);
    }
    
    public String agendaProcedimentosMedico(Medico medico){
        return this.dao.agendaProcedimentosMedico(medico);
    }
    
    public void agendarProcedimento(Medico medico){
        this.dao.agendarProcedimento(medico);
    }
    
    public void realizarProcedimento(Medico medico){
        this.dao.realizarProcedimento(medico);
    }
    
    public void cancelarProcedimento(Medico medico){
        this.dao.cancelarProcedimento(medico);
    }
    
    public void mostrarTodosProcedimentos(){
        this.mostrarTodosProcedimentos();
    }
    
}
