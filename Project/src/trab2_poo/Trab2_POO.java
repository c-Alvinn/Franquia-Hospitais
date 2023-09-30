/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trab2_poo;

import model.Calendario;
import view.Menu;

/**
 *
 * @author Alvin
 */
public class Trab2_POO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Calendario c = new Calendario();
        c.verificarDia1Mes();
        Menu menu = new Menu();
        menu.inicial();
    }

}
