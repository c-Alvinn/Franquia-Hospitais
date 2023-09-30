package controller;

import model.*;

public class UnidadeFController {
    
    private UnidadeFDAO dao = new UnidadeFDAO();
    
    public UnidadeF getUnidadeFUserId(int id){
        return this.dao.getUnidadeFUserId(id);
    }

    public void alterarUnidadeF(Franquia franquia){
        this.dao.alterarUnidadeF(franquia);
    }

    public UnidadeF getUnidadeFZ(){
        return this.dao.getUnidadeFZ();
    }

    public void alterarUnidadeFAdmin(){
        this.dao.alterarUnidadeFAdmin();
    }
}
