package controller;


import model.*;

public class ConsultaController {
    
    private ConsultaDAO dao = new ConsultaDAO();
    
    public String agendaConsultasMedico(Medico medico){
        return this.dao.agendaConsultasMedico(medico);
    }
    
    public void agendarConsulta(Medico medico){
        this.dao.agendarConsulta(medico);
    }
    
    public void realizarConsulta(Medico medico){
        this.dao.realizarConsulta(medico);
    }
    
    public void cancelarConsulta(Medico medico){
        this.dao.cancelarConsulta(medico);
    }
    
    public void mostrarConsultas(){
        this.dao.mostrarConsultas();
    }
}
