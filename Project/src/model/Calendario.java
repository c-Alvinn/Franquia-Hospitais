package model;

import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Calendario {
    
    public void verificarDia1Mes() {
        LocalDate dataAtual = LocalDate.now();
        int diaAtual = dataAtual.getDayOfMonth();

        if (diaAtual == 1) {
            FinanceiroMedDAO f = new FinanceiroMedDAO();
            f.pagar();
            JOptionPane.showMessageDialog(null, "Hoje é dia " + diaAtual + ".\nTodos os pagamentos para os Medicos foram feitos");
        }
    }
    
}
