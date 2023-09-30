package controller;

import model.*;

public class InfoConsultaController {
    
    private InfoConsultaDAO dao = new InfoConsultaDAO();
    
    public void mostrarInfoConsultaPaciente(Pessoa pessoa){
        this.dao.mostrarInfoConsultaPaciente(pessoa);
    }
    
}
