package controller;

import model.*;

public class FinanceiroAdmController {
    
    private FinanceiroAdmDAO dao = new FinanceiroAdmDAO();
    
    public void relatorioFinanceiroAdm(){
        this.dao.relatorioFinanceiroAdm();
    }
    
    public void relatorioFinanceiroAdmUnidade(UnidadeF unidadeF){
        this.dao.relatorioFinanceiroAdmUnidade(unidadeF);
    }
    
    public void relatorioFinanceiroAdmFranquia(Franquia franquia){
        this.dao.relatorioFinanceiroAdmFranquia(franquia);
    }
    
}
