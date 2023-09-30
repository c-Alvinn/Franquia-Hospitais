package controller;

import model.*;

public class MedicoController {
    
    private MedicoDAO dao = new MedicoDAO();
    
    public Medico getMedicoPessoa(Pessoa pessoa){
        return this.dao.getMedicoPessoa(pessoa);
    }
    
    public void alterarMedico(){
        this.dao.alterarMedico();
    }
    
}
