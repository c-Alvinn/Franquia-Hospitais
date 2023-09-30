package controller;

import model.*;

public class FinanceiroMedController {
    
    private FinanceiroMedDAO dao = new FinanceiroMedDAO();
    
    public void relatorioFinanceiroMed(){
        this.dao.relatorioFinanceiroMed();
    }
    
    public void relatorioFinanceiroMedFranquia(Franquia franquia){
        this.dao.relatorioFinanceiroMedFranquia(franquia);
    }
}
