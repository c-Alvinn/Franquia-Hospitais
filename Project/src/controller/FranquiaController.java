package controller;

import model.*;

public class FranquiaController {
    
    private FranquiaDAO dao = new FranquiaDAO();
    
    public Franquia getFranquiaUserId(int id){
        return this.dao.getFranquiaUserId(id);
    }
    
    public void alterarFranquia(Pessoa pessoa){
        this.dao.alterarFranquia(pessoa);
    }
    
    public Franquia getFranquiaZAdmin(){
        return this.dao.getFranquiaZAdmin();
    }
    
}
